package com.example.facetimeclonecompose.domain.models

import com.example.facetimeclonecompose.data.sources.remote.responseModels.ParticipantResponseModel

data class RoomModel(
    val roomId: String,
    val roomType: String,
    val roomAuthor: String,
    val participants: List<ParticipantModel>?,
    val time: String
)
