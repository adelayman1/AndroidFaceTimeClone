package com.example.facetimeclonecompose.presentation.createRoomScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.domain.usecases.CreateAudioRoomUseCase
import com.example.facetimeclonecompose.domain.usecases.CreateVideoRoomUseCase
import com.example.facetimeclonecompose.domain.usecases.GetUserProfileUseCase
import com.example.facetimeclonecompose.domain.usecases.ValidateEmailUseCase
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates.NewRoomUiEvent
import com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates.NewRoomUiState
import com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates.ParticipantUserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    private val createAudioRoomUseCase: CreateAudioRoomUseCase,
    private val createVideoRoomUseCase: CreateVideoRoomUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {
    var createRoomUiState by mutableStateOf(NewRoomUiState(isLoading = false))
        private set;
    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private fun createAudioRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                createRoomUiState = createRoomUiState.copy(isLoading = true)
                val participantsEmails: List<String> = getParticipantsEmails()
                createAudioRoomUseCase(participantsEmails).let {
                    _eventFlow.emit(UiEvent.RoomCreatedSuccessfully)
                }
            } catch (e: Exception) {
                handleGeneralError(e)
            }finally {
                createRoomUiState = createRoomUiState.copy(isLoading = false)
            }
        }
    }

    private fun createVideoRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                createRoomUiState = createRoomUiState.copy(isLoading = true)
                val participantsEmails: List<String> = getParticipantsEmails()
                createVideoRoomUseCase(participantsEmails).let {
                    _eventFlow.emit(UiEvent.RoomCreatedSuccessfully)
                }
            } catch (e: Exception) {
                handleGeneralError(e)
            }finally {
                createRoomUiState = createRoomUiState.copy(isLoading = false)
            }
        }
    }

    private fun addNewParticipant(userEmail: String) {
        viewModelScope.launch {
            try {
                if (isParticipantAlreadyAdded(userEmail)) {
                    _eventFlow.emit(UiEvent.ShowMessage("You have added this user"))
                    return@launch
                }
                // TODO(race condition check and coroutiens cancellable and best practisces check)
                validateEmailUseCase(createRoomUiState.participantsUiState.emailFieldUiState.text)
                addParticipantToUiState(userEmail)
            } catch (e: InvalidInputTextException) {
                handleInvalidInput(e)
            } catch (e: Exception) {
                updateParticipantState(userEmail, false)
                handleGeneralError(e)
            }finally {
                updateButtonsEnableState()
            }
        }
    }

    private suspend fun addParticipantToUiState(userEmail: String){
        addParticipantsToList(userEmail)
        if (isUserExist(userEmail)) {
            updateParticipantState(userEmail, true)
        }
    }

    private suspend fun isUserExist(userEmail: String): Boolean {
        return getUserProfileUseCase(userEmail).email == userEmail
    }

    private fun addParticipantsToList(userEmail: String) {
        val newParticipantsList =
            createRoomUiState.participantsUiState.addedParticipantsUiState.toMutableList()
                .also {
                    it.add(
                        ParticipantUserUiState(
                            userEmail,
                            userExist = false,
                            isLoading = true,
                            onDelete = {
                                createRoomUiState = createRoomUiState.copy(
                                    participantsUiState = createRoomUiState.participantsUiState.copy(
                                        addedParticipantsUiState = createRoomUiState.participantsUiState.addedParticipantsUiState.filterNot {
                                            it.userEmail == userEmail
                                        })
                                )
                                updateButtonsEnableState()
                            }
                        )
                    )
                }
        createRoomUiState = createRoomUiState.copy(
            participantsUiState = createRoomUiState.participantsUiState.copy(
                addedParticipantsUiState = newParticipantsList
            )
        )
    }

    private fun getParticipantsEmails(): List<String> {
        return createRoomUiState.participantsUiState.addedParticipantsUiState.map { userItemUiState -> userItemUiState.userEmail }
    }

    private fun updateParticipantState(userEmail: String, userExists: Boolean) {
        createRoomUiState = createRoomUiState.copy(
            participantsUiState = createRoomUiState.participantsUiState.copy(
                addedParticipantsUiState = createRoomUiState.participantsUiState.addedParticipantsUiState.map {
                    if (it.userEmail == userEmail)
                        it.copy(isLoading = false, userExist = userExists)
                    else
                        it
                }
            )
        )
    }

    private fun updateButtonsEnableState() {
        createRoomUiState =
            createRoomUiState.copy(
                isButtonsEnabled =
                createRoomUiState.participantsUiState.addedParticipantsUiState.isNotEmpty()
                        && !createRoomUiState.participantsUiState.addedParticipantsUiState.any { !it.userExist }
            )
    }

    private fun isParticipantAlreadyAdded(userEmail: String): Boolean {
        return createRoomUiState.participantsUiState.addedParticipantsUiState.any { it.userEmail.trim() == userEmail.trim() }
    }

    private suspend fun handleGeneralError(e: Exception) {
        e.printStackTrace()
        _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
    }

    private fun handleInvalidInput(e: InvalidInputTextException) {
        createRoomUiState = createRoomUiState.copy(
            participantsUiState = createRoomUiState.participantsUiState.copy(
                emailFieldUiState = createRoomUiState.participantsUiState.emailFieldUiState.copy(
                    errorMessage = e.message
                )
            ),
        )
    }

    fun onEvent(action: NewRoomUiEvent) {
        when (action) {
            is NewRoomUiEvent.EmailChanged -> createRoomUiState = createRoomUiState.copy(
                participantsUiState = createRoomUiState.participantsUiState.copy(
                    emailFieldUiState = createRoomUiState.participantsUiState.emailFieldUiState.copy(
                        errorMessage = null,
                        text = action.text
                    )
                )
            )

            NewRoomUiEvent.CreateAudioCall -> createAudioRoom()
            NewRoomUiEvent.CreateVideoCall -> createVideoRoom()
            NewRoomUiEvent.AddNewParticipant -> {
                addNewParticipant(createRoomUiState.participantsUiState.emailFieldUiState.text)
                createRoomUiState = createRoomUiState.copy(
                    participantsUiState = createRoomUiState.participantsUiState.copy(
                        emailFieldUiState = createRoomUiState.participantsUiState.emailFieldUiState.copy(
                            text = ""
                        )
                    )
                )

            }
        }
    }

    sealed class UiEvent {
        data class ShowMessage(var message: String) : UiEvent()
        object RoomCreatedSuccessfully : UiEvent()
    }
}