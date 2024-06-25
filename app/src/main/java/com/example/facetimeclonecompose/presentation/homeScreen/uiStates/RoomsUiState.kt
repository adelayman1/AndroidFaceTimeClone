package com.example.facetimeclonecompose.presentation.homeScreen.uiStates

data class RoomsUiState(
    var isLoading: Boolean = true,
    var noRooms: Boolean = false,
    var rooms: List<RoomItemUiState> = emptyList()
)