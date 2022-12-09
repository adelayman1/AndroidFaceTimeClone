package com.adel.facetimeclone.presentation.outgoingCallScreen.uiStates

import com.adel.facetimeclone.presentation.signUpScreen.uiStates.SignupUiEvent

sealed class OutgoingCallUiEvent {
    object ClosedSuccess:OutgoingCallUiEvent()
    data class ShowMessage(var message: String) : OutgoingCallUiEvent()
}