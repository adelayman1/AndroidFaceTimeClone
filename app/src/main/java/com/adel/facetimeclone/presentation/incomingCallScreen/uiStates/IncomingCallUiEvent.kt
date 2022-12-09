package com.adel.facetimeclone.presentation.incomingCallScreen.uiStates

sealed class IncomingCallUiEvent {
    object Answer:IncomingCallUiEvent()
    object Decline:IncomingCallUiEvent()
    data class ShowMessage(var message: String) : IncomingCallUiEvent()
}