package com.example.facetimeclonecompose.data.sources.remote.responseModels

import com.example.facetimeclonecompose.domain.models.RoomModel
import kotlinx.serialization.Serializable

@Serializable
data class RoomResponseModel(
    val roomId: String,
    val roomType: RoomTypeResponseModel,
    val roomAuthor: String,
    val participants: List<ParticipantResponseModel>?,
    val time: String
) {
    fun toRoomModel() = RoomModel(
        roomId = roomId,
        roomTypeId = roomType.id,
        roomType = roomType.type,
        roomAuthor = roomAuthor,
        participants = participants?.map { it.toParticipantModel() },
        time = time
    )
}