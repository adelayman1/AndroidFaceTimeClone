package com.example.facetimeclonecompose.presentation.otpScreen.uiStates

sealed class OtpUiEvent {
    data class OtpCodeChanged(var code:String): OtpUiEvent()
    object VerifyOTP: OtpUiEvent()
}
