package com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates

data class RoomInvitedUserUiState(
    val userEmail: String = "",
    var userExist: Boolean = false,
    var isLoading: Boolean = false
)