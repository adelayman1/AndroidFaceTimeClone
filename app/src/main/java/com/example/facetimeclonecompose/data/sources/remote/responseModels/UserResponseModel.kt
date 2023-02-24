package com.example.facetimeclonecompose.data.sources.remote.responseModels

import com.example.facetimeclonecompose.domain.models.UserModel

data class UserResponseModel(
    val userID: String,
    val userToken: String? = null,
    val userName: String,
    val email: String,
    val isVerified: Boolean,
    val fcmToken: String? = null,
) {
    fun toUserModel() = UserModel(email = email, isVerified = isVerified, fcmToken = fcmToken, userName = userName)
}