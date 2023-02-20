package com.example.facetimeclonecompose.data.sources.remote.requestModels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RoomTypeEnumRequestModel(val type: String) {
    @SerialName("LINK")
    LINK("LINK"),

    @SerialName("FACETIME")
    FACETIME("FACETIME"),

    @SerialName("AUDIO")
    AUDIO("AUDIO")
}