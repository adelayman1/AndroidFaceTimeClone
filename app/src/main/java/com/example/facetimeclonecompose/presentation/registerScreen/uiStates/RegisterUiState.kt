package com.example.facetimeclonecompose.presentation.registerScreen.uiStates

import com.example.facetimeclonecompose.presentation.loginScreen.uiStates.InputFieldUiState

data class RegisterUiState(
    val isLoading: Boolean = false,
    val nameUiState: InputFieldUiState = InputFieldUiState(),
    val emailUiState: InputFieldUiState = InputFieldUiState(),
    val passwordUiState: InputFieldUiState = InputFieldUiState(),
    val confirmPasswordUiState: InputFieldUiState = InputFieldUiState()
)