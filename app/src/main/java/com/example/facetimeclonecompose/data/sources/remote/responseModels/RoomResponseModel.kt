package com.example.facetimeclonecompose.data.sources.remote.responseModels

import com.example.facetimeclonecompose.domain.models.RoomModel

data class RoomResponseModel(
    val roomId: String,
    val roomType: String,
    val roomAuthor: String,
    val participants: List<ParticipantResponseModel>?,
    val time: String
) {
    fun toRoomModel() = RoomModel(
        roomId = roomId,
        roomType = roomType,
        roomAuthor = roomAuthor,
        participants = participants?.map { it.toParticipantModel() },
        time = time
    )
}