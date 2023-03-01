package com.example.facetimeclonecompose.domain.models

data class RoomModel(
    val roomId: String,
    val roomType: String,
    val roomAuthor: String,
    val participants: List<ParticipantModel>?,
    val time: String
)
