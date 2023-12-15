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
// TODO("EMAIL VALIDATE AND EMAIL ERROR HANDLE AND")
    private fun createAudioRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                createRoomUiState = createRoomUiState.copy(isLoading = true)
                val participantsEmails: List<String> =
                    createRoomUiState.participantsUiState.addedParticipantsUiState.map { userItemUiState -> userItemUiState.userEmail }
                createAudioRoomUseCase(participantsEmails).also {
                    _eventFlow.emit(UiEvent.AudioCallCreatedSuccessfully(userName = getUserProfileUseCase().userName,roomKey=it.roomId))
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
                val participantsEmails: List<String> =
                    createRoomUiState.participantsUiState.addedParticipantsUiState.map { userItemUiState -> userItemUiState.userEmail }
                createVideoRoomUseCase(participantsEmails).also {
                    _eventFlow.emit(UiEvent.VideoCallCreatedSuccessfully(userName = getUserProfileUseCase().userName, roomKey = it.roomId))
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
                val isUserAdded =
                    createRoomUiState.participantsUiState.addedParticipantsUiState.any { it.userEmail.trim() == userEmail.trim() }
                if (isUserAdded) {
                    _eventFlow.emit(UiEvent.ShowMessage("You have added this user"))
                    return@launch
                }
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
                // TODO()
                getUserProfileUseCase(userEmail).apply {
                    createRoomUiState = createRoomUiState.copy(
                        participantsUiState = createRoomUiState.participantsUiState.copy(
                            addedParticipantsUiState = createRoomUiState.participantsUiState.addedParticipantsUiState.map {
                                if (it.userEmail == userEmail)
                                    it.copy(isLoading = false, userExist = true)
                                else
                                    it
                            })
                    )
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
            } catch (e: Exception) {
                createRoomUiState = createRoomUiState.copy(
                    participantsUiState = createRoomUiState.participantsUiState.copy(
                        addedParticipantsUiState = createRoomUiState.participantsUiState.addedParticipantsUiState.map {
                            if (it.userEmail == userEmail)
                                it.copy(isLoading = false, userExist = false)
                            else
                                it
                        })
                )
                updateButtonsEnableState()
//                val newParticipantsList = createRoomUiState.participantsUiState.addedParticipantsUiState
//                newParticipantsList.find { it.userEmail.trim() == userEmail.trim() }!!.let {
//                    it.isLoading = false
//                    it.userExist = false
//                }
//                createRoomUiState = createRoomUiState.copy(
//                    participantsUiState = createRoomUiState.participantsUiState.copy(
//                        addedParticipantsUiState =  newParticipantsList
//                    )
//                )
                //TODO()
//                createRoomUiState = createRoomUiState.copy(
//                    participantsUiState = createRoomUiState.participantsUiState.copy(
//                        emailFieldUiState = createRoomUiState.participantsUiState.emailFieldUiState.copy(
//                            errorMessage = null,
//                            text = "a"
//                        )
//                    )
//                )
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
    private fun updateButtonsEnableState(){
        createRoomUiState = createRoomUiState.copy(isButtonsEnabled = !createRoomUiState.participantsUiState.addedParticipantsUiState.any { !it.userExist })
    }

    sealed class UiEvent {
        data class ShowMessage(var message: String) : UiEvent()
        data class AudioCallCreatedSuccessfully(var userName: String,var roomKey:String) : UiEvent()
        data class VideoCallCreatedSuccessfully(var userName: String,var roomKey:String) : UiEvent()
    }
}