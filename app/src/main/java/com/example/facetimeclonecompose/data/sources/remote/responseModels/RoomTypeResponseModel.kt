package com.example.facetimeclonecompose.data.sources.remote.responseModels

import kotlinx.serialization.Serializable

@Serializable
data class RoomTypeResponseModel(
    val id: Int,
    val type: String
)