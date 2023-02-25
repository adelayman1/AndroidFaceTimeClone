package com.example.facetimeclonecompose.domain.models

import com.example.facetimeclonecompose.data.sources.remote.requestModels.RoomTypeEnumRequestModel

enum class RoomTypeModel(val type: String) {
    LINK("LINK"),
    FACETIME("FACETIME"),
    AUDIO("AUDIO")
}
fun RoomTypeModel.toRoomTypeEnumRequestModel():RoomTypeEnumRequestModel {
    return when(this.type) {
        "LINK" -> RoomTypeEnumRequestModel.LINK
        "FACETIME" -> RoomTypeEnumRequestModel.FACETIME
        "AUDIO" -> RoomTypeEnumRequestModel.AUDIO
        else -> RoomTypeEnumRequestModel.LINK
    }
}