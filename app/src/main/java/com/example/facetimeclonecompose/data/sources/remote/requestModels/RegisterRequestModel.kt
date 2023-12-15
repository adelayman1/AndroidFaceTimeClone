package com.example.facetimeclonecompose.data.sources.remote.requestModels

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestModel(
    val email: String,
    val name: String,
    val password: String
)