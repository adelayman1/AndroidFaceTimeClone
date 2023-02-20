package com.example.facetimeclonecompose.data.sources.remote.responseModels

data class UserResponseModel(
    val userID: String,
    val userToken: String? = null,
    val userName: String,
    val email: String,
    val isVerified: Boolean,
    val fcmToken: String? = null,
)