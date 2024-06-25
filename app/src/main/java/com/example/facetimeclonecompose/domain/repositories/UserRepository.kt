package com.example.facetimeclonecompose.domain.repositories

import com.example.facetimeclonecompose.domain.models.UserModel

interface UserRepository {
    suspend fun login(email: String, password: String):UserModel?

    suspend fun createNewAccount(name: String, email: String, password: String): UserModel?

    suspend fun sendVerificationEmail()

    suspend fun verifyOtpCode(otpCode: Int):UserModel

    suspend fun deleteUserAccount()

    suspend fun updateUserFcmToken(fcmToken:String)

    suspend fun isUserLoggedIn():Boolean

    suspend fun isUserAccountVerified(): Boolean

    suspend fun getUserProfileDataByEmail(userEmail: String): UserModel

    suspend fun getUserProfileDataById(userId:String): UserModel

    suspend fun getUserProfileDataById(): UserModel

    suspend fun getUserID(): String?

    suspend fun getFCMToken(): String
}