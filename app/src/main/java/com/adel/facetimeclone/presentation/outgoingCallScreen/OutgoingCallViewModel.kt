package com.adel.facetimeclone.presentation.outgoingCallScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.domain.usecases.KickOutAllRoomUsersUseCase
import com.adel.facetimeclone.presentation.outgoingCallScreen.uiStates.OutgoingCallUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OutgoingCallViewModel @Inject constructor(
    val kickOutAllRoomUsersUseCase: KickOutAllRoomUsersUseCase
) : ViewModel() {
    private var _eventFlow = MutableSharedFlow<OutgoingCallUiEvent>()
    val eventFlow: SharedFlow<OutgoingCallUiEvent> = _eventFlow.asSharedFlow()
    fun closeRoom(roomKey: String,users:List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                kickOutAllRoomUsersUseCase.invoke(roomKey,users).also {
                    _eventFlow.emit(OutgoingCallUiEvent.ClosedSuccess)
                }
            }catch (e:Exception){
                _eventFlow.emit(OutgoingCallUiEvent.ShowMessage(e.message.toString()))
            }
        }
    }
}