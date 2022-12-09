package com.adel.facetimeclone.presentation.incomingCallScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.usecases.AgreeCallUseCase
import com.adel.facetimeclone.domain.usecases.DeclineCallUseCase
import com.adel.facetimeclone.presentation.incomingCallScreen.uiStates.IncomingCallUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomingCallViewModel @Inject constructor(
    val agreeCallUseCase: AgreeCallUseCase,
    val declineCallUseCase: DeclineCallUseCase
) : ViewModel() {
    private var _eventFlow = MutableSharedFlow<IncomingCallUiEvent>()
    val eventFlow: SharedFlow<IncomingCallUiEvent> = _eventFlow.asSharedFlow()
    fun agreeCall(roomKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                agreeCallUseCase.invoke(roomKey).also {
                    _eventFlow.emit(IncomingCallUiEvent.Answer)
                }
            } catch (e: Exception) {
                _eventFlow.emit(IncomingCallUiEvent.ShowMessage(e.message.toString()))
            }

        }
    }

    fun declineCall(roomKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                declineCallUseCase.invoke(roomKey).also {
                    _eventFlow.emit(IncomingCallUiEvent.Decline)
                }
            } catch (e: Exception) {
                _eventFlow.emit(IncomingCallUiEvent.ShowMessage(e.message.toString()))
            }
        }
    }

}