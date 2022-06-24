package com.adel.facetimeclone.data.repository

import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    override suspend fun isUserInDatabase(userEmail: String): Boolean {
        return try {
            val userReference =
                    FirebaseDatabase.getInstance().getReference("Tokens")
                            .child(userEmail.trim().replace(".", ",")).get().await()
            userReference.exists()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun loginWithEmailAndPassword(
            email: String,
            password: String
    ): Result<FirebaseUser> {
        return try {
            val signInWithEmailAndPasswordResult =
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            Result.Success(signInWithEmailAndPasswordResult.user!!)
        } catch (e: Exception) {
            Result.Error(ErrorEntity.AuthError,e.message.toString())
        }
    }

    override suspend fun createAccountWithEmailAndPassword(
            email: String,
            password: String
    ): Result<FirebaseUser> {
        return try {
            val createUserWithEmailAndPasswordResult =
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            Result.Success(createUserWithEmailAndPasswordResult.user!!)
        } catch (e: Exception) {
            Result.Error(ErrorEntity.AuthError,e.message.toString())
        }
    }

    override suspend fun updateUserData(email: String, name: String): Result<Any> {
        return try {
            val userReference =
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child((email.replace(".", ",")))
            userReference.child("name").setValue(name).await()
            userReference.child("email").setValue(email).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(ErrorEntity.WriteDataError,e.message.toString())
        }
    }

    override suspend fun getUserName(userEmail: String): Result<String> {
        return try {
            val userName =
                    FirebaseDatabase.getInstance().getReference("Users").child(userEmail.replace(".",",")).child("name")
                            .get().await()
            Result.Success(userName.getValue(String::class.java)!!)
        } catch (e: Exception) {
            Result.Error(ErrorEntity.GetDataError,e.message.toString())
        }
    }

    override fun getUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser


    override suspend fun getUserToken(userEmail: String): Result<String> {
        return try {
            val userTOKEN =
                    FirebaseDatabase.getInstance().getReference("Tokens")
                            .child(userEmail.replace(".", ",")).get().await()
            Result.Success((userTOKEN.getValue(String::class.java)).toString())
        } catch (e: Exception) {
            Result.Error(ErrorEntity.GetDataError,e.message.toString())
        }
    }

    override suspend fun getToken(): Result<String> {
        return try {
            val token = FirebaseMessaging.getInstance().token.await()
            Result.Success(token)
        } catch (e: Exception) {
            Result.Error(ErrorEntity.GetDataError,e.message.toString())
        }
    }

    override suspend fun updateUserToken(email: String, token: String): Result<Any> {
        return try {
            val updateTokenResult =
                    FirebaseDatabase.getInstance().getReference("Tokens").child(email.replace(".", ","))
                            .setValue(token).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(ErrorEntity.WriteDataError,e.message.toString())
        }
    }
}