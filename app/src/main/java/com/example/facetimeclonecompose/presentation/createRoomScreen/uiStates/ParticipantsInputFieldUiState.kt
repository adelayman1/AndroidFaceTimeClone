package com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates

import com.example.facetimeclonecompose.presentation.loginScreen.uiStates.InputFieldUiState

data class ParticipantsInputFieldUiState(
    val emailFieldUiState:InputFieldUiState = InputFieldUiState(),
    val addedParticipantsUiState:List<RoomInvitedUserUiState> = emptyList()
)