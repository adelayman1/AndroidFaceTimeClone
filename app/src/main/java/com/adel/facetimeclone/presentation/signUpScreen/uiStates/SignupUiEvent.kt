package com.adel.facetimeclone.presentation.signUpScreen.uiStates

sealed class SignupUiEvent {
    object SignupSuccess : SignupUiEvent()
    data class ShowMessage(var message: String) : SignupUiEvent()
}