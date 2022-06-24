package com.adel.facetimeclone.domain.repository

import com.adel.facetimeclone.domain.entities.Result
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    suspend fun isUserInDatabase(userEmail: String) : Boolean
    suspend fun getUserName(userEmail: String):  Result<String>
    suspend fun getUserToken(userEmail: String): Result<String>
    fun getUser(): FirebaseUser?
    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Result<FirebaseUser>
    suspend fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): Result<FirebaseUser>
    suspend fun updateUserData(email: String, name: String): Result<Any>
    suspend fun getToken(): Result<String>
    suspend fun updateUserToken(email: String,token:String): Result<Any>
}