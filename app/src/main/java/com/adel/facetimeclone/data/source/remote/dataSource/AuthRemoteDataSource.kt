package com.adel.facetimeclone.data.source.remote.dataSource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    var authInstance: FirebaseAuth,
    var ioDispatcher: CoroutineDispatcher
) {

    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser = withContext(ioDispatcher) {
        return@withContext authInstance.signInWithEmailAndPassword(email, password).await().user!!
    }

    suspend fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser = withContext(ioDispatcher) {
        return@withContext FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password).await().user!!
    }
}