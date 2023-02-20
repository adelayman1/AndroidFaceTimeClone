package com.example.facetimeclonecompose.data.sources.remote.requestModels

import kotlinx.serialization.Serializable

@Serializable
data class CreateRoomRequestModel(
    val roomType: RoomTypeEnumRequestModel,
    val participantsEmails: List<String>?
)