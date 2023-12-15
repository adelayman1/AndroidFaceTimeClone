package com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates

data class ParticipantUserUiState(
    val userEmail: String = "",
    var userExist: Boolean = false,
    var isLoading: Boolean = false,
    val onDelete: () -> Unit
)