package com.adel.facetimeclone.presentation.newFaceTimeScreen.uiStates

data class FaceTimeUiState(
    var isLoading: Boolean = false,
    var type: String = "AudioCall",
    var addedUsers: List<UserItemUiState> = emptyList()
)