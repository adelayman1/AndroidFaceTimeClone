package com.adel.facetimeclone.presentation.homeScreen.uiStates

import com.adel.facetimeclone.presentation.signUpScreen.uiStates.SignupUiEvent

sealed class HomeUiEvent {
    data class ShowMessage(var message: String) : HomeUiEvent()
}