package com.example.facetimeclonecompose.presentation.otpScreen.uiStates

import com.example.facetimeclonecompose.presentation.homeScreen.uiStates.ItemPosition
import com.example.facetimeclonecompose.presentation.loginScreen.uiStates.InputFieldUiState

data class OtpUiState(
    var codeLength:Int=4,
    var initialCode:InputFieldUiState = InputFieldUiState(),
    var isLoading:Boolean = false
)