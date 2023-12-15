package com.example.facetimeclonecompose.data.sources.remote.requestModels

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestModel(
    val email: String,
    val password: String
)