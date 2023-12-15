package com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates

data class NewRoomUiState(
    val isLoading: Boolean = false,
    val participantsUiState:ParticipantsInputFieldUiState = ParticipantsInputFieldUiState(),
    val isButtonsEnabled:Boolean = false
)