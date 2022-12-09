package com.adel.facetimeclone.domain.repository

import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    suspend fun isUserExist(userEmail: String) : Boolean
    suspend fun getUserName(userEmail: String):  String?
    suspend fun getUserToken(): String
    suspend fun getUserToken(userEmail: String): String?
    fun getUser(): FirebaseUser?
    suspend fun updateUserData(email: String, name: String)
    suspend fun updateUserToken(email: String,token:String): Any
    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser
    suspend fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser

}