package com.adel.facetimeclone.presentation.homeScreen.uiStates

import com.adel.facetimeclone.data.model.ParticipantModel

data class CallItemUiState(
    var roomType: String? = null,
    var roomAuthor: String? = null,
    var to: Map<String, ParticipantModel>? = HashMap(),
    var time: String = ""
)