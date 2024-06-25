package com.example.facetimeclonecompose.presentation.splashScreen.uiStates

import com.example.facetimeclonecompose.presentation.utilities.Screen

data class SplashUiState(
    val isLoading: Boolean = true,
    val startDestination:String = Screen.LoginScreen.route,
)