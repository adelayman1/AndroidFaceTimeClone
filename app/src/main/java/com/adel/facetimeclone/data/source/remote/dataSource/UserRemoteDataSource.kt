package com.adel.facetimeclone.data.source.remote.dataSource

import com.adel.facetimeclone.data.model.UserModel
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    var dbReference: DatabaseReference,
    var ioDispatcher: CoroutineDispatcher
) {
    suspend fun isUserExist(userEmail: String): Boolean =
        withContext(ioDispatcher) {
            dbReference.child("Users").child(userEmail).get().await().exists()
        }

    suspend fun updateUserData(userEmail:String,data:HashMap<String,String>): Any = withContext(ioDispatcher) {
        val userReference = dbReference.child("Users")
            .child(userEmail).setValue(data)
        true
    }

    suspend fun getUserData(userEmail: String): UserModel? = withContext(ioDispatcher) {
        dbReference.child("Users").child(userEmail)
            .get().await().getValue(UserModel::class.java)
    }
}