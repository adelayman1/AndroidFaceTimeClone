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
                val participantsEmails: List<String> =  getParticipantsEmails()
                createAudioRoomUseCase(participantsEmails).also {
                    _eventFlow.emit(
                        UiEvent.AudioCallCreatedSuccessfully(
                            userName = getUserProfileUseCase().userName,
                            roomKey = it.roomId
                        )
                    )
                }
            } catch (e: Exception) {
                createRoomUiState = createRoomUiState.copy(isLoading = false)
                _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
            }
        }
    }

    private fun createVideoRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                createRoomUiState = createRoomUiState.copy(isLoading = true)
                val participantsEmails: List<String> = getParticipantsEmails()
                createVideoRoomUseCase(participantsEmails).also {
                    _eventFlow.emit(
                        UiEvent.VideoCallCreatedSuccessfully(
                            userName = getUserProfileUseCase().userName,
                            roomKey = it.roomId
                        )
                    )
                }
            } catch (e: Exception) {
                createRoomUiState = createRoomUiState.copy(isLoading = false)
                _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
            }
        }
    }

    private fun addUser(userEmail: String) {
        viewModelScope.launch {
            try {
                if (isUserInParticipantsList(userEmail)) {
                    _eventFlow.emit(UiEvent.ShowMessage("You have added this user"))
                    return@launch
                }
                // TODO(race condition check and coroutiens cancellable and best practisces check)

                val emailValidationResult = validateEmailUseCase(createRoomUiState.participantsUiState.emailFieldUiState.text)
                if (emailValidationResult.error != null) {
                    createRoomUiState = createRoomUiState.copy(
                        participantsUiState = createRoomUiState.participantsUiState.copy(
                            emailFieldUiState = createRoomUiState.participantsUiState.emailFieldUiState.copy(
                                errorMessage = emailValidationResult.error
                            )
                        ),
                    )
                    createRoomUiState = createRoomUiState.copy(isLoading = false)
                    return@launch
                }

                addNewParticipantToList(userEmail)
                getUserProfileUseCase(userEmail).apply {
                    updateParticipantState(userEmail,true)
                    updateButtonsEnableState()
                }


            } catch (e: InvalidInputTextException) {
                createRoomUiState = createRoomUiState.copy(
                    participantsUiState = createRoomUiState.participantsUiState.copy(
                        emailFieldUiState = createRoomUiState.participantsUiState.emailFieldUiState.copy(
                            errorMessage = e.message
                        )
                    ),
                )
                updateParticipantState(userEmail,false)
            } catch (e: Exception) {
                updateParticipantState(userEmail,false)
                updateButtonsEnableState()
                _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
            }
        }
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
                addUser(createRoomUiState.participantsUiState.emailFieldUiState.text)
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

    private fun addNewParticipantToList(userEmail: String) {
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
    private fun getParticipantsEmails():List<String>{
        return createRoomUiState.participantsUiState.addedParticipantsUiState.map { userItemUiState -> userItemUiState.userEmail }
    }
    private fun updateParticipantState(userEmail: String, userExists:Boolean){
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
    private fun isUserInParticipantsList(userEmail: String):Boolean{
        return createRoomUiState.participantsUiState.addedParticipantsUiState.any { it.userEmail.trim() == userEmail.trim() }
     }
    private fun updateButtonsEnableState() {
        createRoomUiState =
            createRoomUiState.copy(isButtonsEnabled = !createRoomUiState.participantsUiState.addedParticipantsUiState.any { !it.userExist })
    }

    sealed class UiEvent {
        data class ShowMessage(var message: String) : UiEvent()
        data class AudioCallCreatedSuccessfully(var userName: String, var roomKey: String) :
            UiEvent()

        data class VideoCallCreatedSuccessfully(var userName: String, var roomKey: String) :
            UiEvent()
    }
}