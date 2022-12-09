package com.adel.facetimeclone.presentation.loginScreen.uiStates

sealed class LoginUiEvent {
    object LoginSuccess : LoginUiEvent()
    data class ShowMessage(var message: String) : LoginUiEvent()
}