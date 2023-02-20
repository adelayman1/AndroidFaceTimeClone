package com.example.facetimeclonecompose.data.sources.remote.responseModels

data class RoomResponseModel(
    val roomId: String,
    val roomType: String,
    val roomAuthor: String,
    val participants: List<ParticipantResponseModel>?,
    val time: String
)