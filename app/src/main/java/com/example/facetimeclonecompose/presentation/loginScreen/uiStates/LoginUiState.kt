package com.example.facetimeclonecompose.presentation.loginScreen.uiStates

data class LoginUiState(
    val isLoading: Boolean = false,
    val emailUiState: InputFieldUiState = InputFieldUiState(),
    val passwordUiState: InputFieldUiState = InputFieldUiState()
)