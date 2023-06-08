package com.example.facetimeclonecompose.data.sources.remote.responseModels

import com.example.facetimeclonecompose.domain.models.UserModel
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseModel(
    val userID: String,
    val userToken: String? = null,
    val userName: String,
    val email: String,
    val isVerified: Boolean,
    val fcmToken: String? = null,
) {
    fun toUserModel() = UserModel(email = email, isVerified = isVerified, userId = userID, userName = userName)
}