package com.example.facetimeclonecompose.domain.models

data class UserModel(
    val email: String,
    val isVerified: Boolean,
    val fcmToken: String? = null,
    val userName: String
)