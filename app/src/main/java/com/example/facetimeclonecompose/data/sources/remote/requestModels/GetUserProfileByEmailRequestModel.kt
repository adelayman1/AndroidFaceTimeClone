package com.example.facetimeclonecompose.data.sources.remote.requestModels

import kotlinx.serialization.Serializable

@Serializable
data class GetUserProfileByEmailRequestModel(
    val email: String
)