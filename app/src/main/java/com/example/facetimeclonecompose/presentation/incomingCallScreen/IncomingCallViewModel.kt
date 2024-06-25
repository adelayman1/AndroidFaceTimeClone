package com.example.facetimeclonecompose.presentation.incomingCallScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetimeclonecompose.domain.usecases.JoinRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomingCallViewModel @Inject constructor(private val joinRoomUseCase: JoinRoomUseCase): ViewModel(){

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun joinRoom(roomId: String){
        viewModelScope.launch {
            try {
                joinRoomUseCase(roomId).apply {
                    _eventFlow.emit(UiEvent.JoinedSuccessfully)
                }
            } catch (e: Exception) {
                handleGeneralError(e)
            }
        }
    }
    
    private suspend fun handleGeneralError(e: Exception) {
        e.printStackTrace()
        _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
    }
    
    sealed class UiEvent {
        data class ShowMessage(var message: String) : UiEvent()
        object JoinedSuccessfully : UiEvent()
    }
}