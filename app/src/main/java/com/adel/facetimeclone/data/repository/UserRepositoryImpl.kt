package com.adel.facetimeclone.data.repository

import com.adel.facetimeclone.data.source.remote.dataSource.AuthRemoteDataSource
import com.adel.facetimeclone.data.source.remote.dataSource.TokenRemoteDataSource
import com.adel.facetimeclone.data.source.remote.dataSource.UserRemoteDataSource
import com.adel.facetimeclone.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    var userRemoteDataSource: UserRemoteDataSource,
    var authRemoteDataSource: AuthRemoteDataSource,
    var externalScope: CoroutineScope,
    var tokenRemoteDataSource: TokenRemoteDataSource
) : UserRepository {

    override suspend fun isUserExist(userEmail: String): Boolean {
        val user = userRemoteDataSource.getUserData(userEmail.trim().replace(".", ","))
        return user != null
    }

    override suspend fun loginWithEmailAndPassword(
        email: String, password: String
    ): FirebaseUser {
        return externalScope.async {
            authRemoteDataSource.loginWithEmailAndPassword(email, password).also {
                if (it.uid.isEmpty()) throw Exception("UID NOT FOUND")
            }
        }.await()
    }

    override suspend fun createAccountWithEmailAndPassword(
        email: String, password: String
    ): FirebaseUser {
        return externalScope.async {
            authRemoteDataSource.createAccountWithEmailAndPassword(email, password).also {
                if (it.uid.isEmpty()) throw Exception("UID NOT FOUND")
            }
        }.await()
    }

    override suspend fun updateUserData(email: String, name: String) {
        externalScope.launch {
            val data = hashMapOf("email" to email, "name" to name)
            userRemoteDataSource.updateUserData(email.trim().replace(".", ","), data)
        }.join()
    }

    override suspend fun getUserName(userEmail: String): String? =
        userRemoteDataSource.getUserData(userEmail.trim().replace(".", ","))?.name

    //TODO AUTH
    override fun getUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser


    override suspend fun getUserToken(userEmail: String): String? {
        return tokenRemoteDataSource.getToken(userEmail.trim().replace(".", ","))
    }

    override suspend fun getUserToken(): String {
        return tokenRemoteDataSource.getToken()
    }

    override suspend fun updateUserToken(email: String, token: String): Any {
        return tokenRemoteDataSource.updateToken(email.replace(".", ","), token)
    }
}