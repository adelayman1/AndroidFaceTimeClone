package com.example.facetimeclonecompose.domain.models

data class UserModel(
    val email: String,
    val isVerified: Boolean,
    val userId: String,
    val userName: String
)