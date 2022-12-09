package com.adel.facetimeclone.data.source.remote.dataSource

import com.google.firebase.database.DatabaseReference
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenRemoteDataSource @Inject constructor(
    var dbReference: DatabaseReference,
    var firebaseMessaging: FirebaseMessaging,
    var ioDispatcher: CoroutineDispatcher
) {

    suspend fun getToken(userEmail: String): String? = withContext(ioDispatcher) {
        dbReference.child("Tokens").child(userEmail).get().await().getValue(String::class.java)
    }

    suspend fun updateToken(email: String, token: String): Any = withContext(ioDispatcher) {
        dbReference.child("Tokens").child(email.replace(".", ","))
            .setValue(token).await()
        true
    }

    suspend fun getToken(): String = withContext(ioDispatcher) {
        return@withContext firebaseMessaging.token.await()
    }
}