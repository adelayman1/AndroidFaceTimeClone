package com.example.facetimeclonecompose.presentation.otpScreen.uiStates

import com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates.NewRoomUiEvent

sealed class OtpUiEvent {
    data class OtpCodeChanged(var code:String): OtpUiEvent()
    object VerifyOTP: OtpUiEvent()
}
