package com.example.facetimeclonecompose.presentation.loginScreen.uiStates

sealed class LoginUiEvent {
    data class EmailChanged(var text:String):LoginUiEvent()
    data class PasswordChanged(var text:String):LoginUiEvent()
}