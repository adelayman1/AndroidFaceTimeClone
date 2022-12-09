package com.adel.facetimeclone.data.source.remote.dataSource

import com.adel.facetimeclone.data.model.RoomModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRemoteDataSource @Inject constructor(
    var dbReference: DatabaseReference,
    var ioDispatcher: CoroutineDispatcher
) {
    suspend fun createRoom(roomModel: RoomModel): String? =
        withContext(ioDispatcher) {
            val room = dbReference.child("Rooms").push()
            room.setValue(roomModel).await()
            room.key
        }

    suspend fun changeRoomUserMissedCallValue(
        roomKey: String,
        userEmail: String,
        isMissedCall: Boolean
    ): Boolean = withContext(ioDispatcher) {
        dbReference.child("Rooms").child(roomKey).child("to")
            .child(userEmail).child("missedCall").setValue(isMissedCall).await()
        true
    }

    @ExperimentalCoroutinesApi
    suspend fun getAllUserRooms(userEmail: String): Flow<List<RoomModel>> =
        withContext(ioDispatcher) {
            val roomsTempList: MutableList<RoomModel> = mutableListOf()
            callbackFlow {
                val roomsListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        roomsTempList.clear()
                        for (item in snapshot.children) {
                            if (item.child("roomAuthor")
                                    .getValue(String::class.java) == userEmail || item.child("to")
                                    .child(userEmail.replace(".", ",")).exists()
                            ) {
                                roomsTempList.add(0, item.getValue(RoomModel::class.java)!!)
                            }
                        }
                        this@callbackFlow.trySendBlocking(roomsTempList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        throw error.toException()
                    }
                }
                dbReference.child("Rooms")
                    .orderByChild("time")
                    .addValueEventListener(roomsListener)

                awaitClose {
                    dbReference.child("Rooms").orderByChild("time")
                        .removeEventListener(roomsListener)
                }
            }
        }
}