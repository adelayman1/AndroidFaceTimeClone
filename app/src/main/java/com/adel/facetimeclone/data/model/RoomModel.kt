package com.adel.facetimeclone.data.model

data class RoomModel(
    val roomType: String? = "",
    val roomAuthor: String? = "",
    val to: Map<String, ParticipantModel>? = HashMap(),
    val time: String? = ""
)