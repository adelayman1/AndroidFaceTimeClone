package com.example.facetimeclonecompose.presentation.utilities

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object RegisterScreen: Screen("register_screen")
    object LoginScreen: Screen("login_screen")
    object CreateRoomScreen: Screen("create_room_screen")
    object OtpCodeScreen: Screen("otp_code_screen")
}
