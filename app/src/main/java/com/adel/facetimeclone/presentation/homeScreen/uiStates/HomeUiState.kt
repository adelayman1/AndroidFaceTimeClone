package com.adel.facetimeclone.presentation.homeScreen.uiStates

data class HomeUiState(
    var isLoading: Boolean = true,
    var callsList: List<CallItemUiState> = emptyList(),
    var isLinkDialogVisible: Boolean = false,
    var roomLink:String? = null
)