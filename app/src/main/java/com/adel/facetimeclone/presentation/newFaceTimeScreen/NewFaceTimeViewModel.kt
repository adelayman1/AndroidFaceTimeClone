package com.adel.facetimeclone.presentation.newFaceTimeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.usecases.CheckIsUserExistUseCase
import com.adel.facetimeclone.domain.usecases.CreateRoomCallUseCase
import com.adel.facetimeclone.presentation.newFaceTimeScreen.uiStates.FaceTimeUiEvent
import com.adel.facetimeclone.presentation.newFaceTimeScreen.uiStates.FaceTimeUiState
import com.adel.facetimeclone.presentation.newFaceTimeScreen.uiStates.UserItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFaceTimeViewModel @Inject constructor(
    var checkIsUserExistUseCase: CheckIsUserExistUseCase,
    var createRoomCallUseCase: CreateRoomCallUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(FaceTimeUiState())
    val uiState: StateFlow<FaceTimeUiState> = _uiState.asStateFlow()

    private var _eventFlow = MutableSharedFlow<FaceTimeUiEvent>()
    val eventFlow: SharedFlow<FaceTimeUiEvent> = _eventFlow.asSharedFlow()
    fun addUser(userEmail: String) {
        viewModelScope.launch {
            try {
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = true,
                    )
                }
                var isUserAdded =
                    _uiState.value.addedUsers.any { it.userEmail.trim() == userEmail.trim() }
                if (isUserAdded) {
                    _eventFlow.emit(FaceTimeUiEvent.ShowMessage("You have added this user"))
                    return@launch
                }
                val isUserExist = checkIsUserExistUseCase.invoke(userEmail)
                val newUsersList = _uiState.value.addedUsers.toMutableList()
                newUsersList.add(UserItemUiState(isUserExist.first, isUserExist.second))
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = false,
                        addedUsers = newUsersList
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = false
                    )
                }
                _eventFlow.emit(FaceTimeUiEvent.ShowMessage(e.message.toString()))
            }
        }
    }

    fun call() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = true
                    )
                }
                var usersEmails: List<String> =
                    _uiState.value.addedUsers.map { userItemUiState -> userItemUiState.userEmail }
                createRoomCallUseCase.invoke(usersEmails, uiState.value.type).also {
                    _eventFlow.emit(FaceTimeUiEvent.CallCreatedSuccessfully(it.first,it.second))
                }
            } catch (e: Exception) {
                Log.d("TAGTAGTAGTAGTAGTAGTAGTAGTAGTAGTAG", "call: ${e}${e.message}")
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        isLoading = false
                    )
                }
                _eventFlow.emit(FaceTimeUiEvent.ShowMessage(e.message.toString()))
            }
        }
    }

    fun changeTypeToAudioCall() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                type = "AudioCall"
            )
        }
    }

    fun changeTypeToVideoCall() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                type = "FaceTime"
            )
        }
    }
}