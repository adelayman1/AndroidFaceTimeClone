package com.example.facetimeclonecompose.presentation.utilities

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
}
