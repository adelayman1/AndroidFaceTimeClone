package com.example.facetimeclonecompose.presentation.registerScreen.uiStates

sealed class RegisterUiEvent {
    data class NameChanged(var text:String):RegisterUiEvent()
    data class EmailChanged(var text:String):RegisterUiEvent()
    data class PasswordChanged(var text:String):RegisterUiEvent()
    data class ConfirmPasswordChanged(var text:String):RegisterUiEvent()
}