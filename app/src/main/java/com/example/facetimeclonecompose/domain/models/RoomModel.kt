package com.example.facetimeclonecompose.domain.models

data class RoomModel(
    //TODO("RoomType")
    val roomId: String,
    val roomTypeId: Int,
    val roomType: String,
    val roomTitle:String? = null,
    val roomAuthor: String,
    val participants: List<ParticipantModel>?,
    var time: String,
    var isMissed: Boolean = false
)
