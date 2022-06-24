package com.adel.facetimeclone.data.repository

import com.adel.facetimeclone.data.api.FcmApiService
import com.adel.facetimeclone.data.model.CallInvitationDataModel
import com.adel.facetimeclone.data.model.CallInvitationModel
import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.domain.repository.RoomRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(var fcmApiService: FcmApiService) : RoomRepository {
    override suspend fun createRoom(roomModel: RoomModel): Result<String> {
        return try {
            val room = FirebaseDatabase.getInstance().getReference("Rooms").push()
            room.setValue(roomModel).await()
            Result.Success(room.key!!)
        } catch (e: Exception) {
            Result.Error(ErrorEntity.WriteDataError,e.message.toString())
        }
    }

    override suspend fun changeIsUserMissedCallValue(
        roomKey: String,
        userEmail: String,
        value: Boolean
    ): Result<Any> {


        return try {
            FirebaseDatabase.getInstance().getReference("Rooms").child(roomKey).child("to")
                .child(userEmail.replace(".",",")).child("missedCall").setValue(value).await()
            Result.Success(Any())
        } catch (e: Exception) {
            Result.Error(ErrorEntity.WriteDataError,e.message.toString())
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getAllUserCalls(userEmail: String): Flow<Result<List<RoomModel>>> {
        val roomsTempList: MutableList<RoomModel> = mutableListOf()
        return callbackFlow {

            val roomsListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    roomsTempList.clear()
                    for (item in snapshot.children) {
                        if (item.child("roomAuthor")
                                .getValue(String::class.java) == userEmail || item.child("to")
                                .child(userEmail.replace(".",",")).exists()
                        ) {
                            roomsTempList.add(0, item.getValue(RoomModel::class.java)!!)
                        }
                    }
                    this@callbackFlow.trySendBlocking(Result.Success(roomsTempList))
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
            FirebaseDatabase.getInstance().reference.child("Rooms")
                .orderByChild("time")
                .addValueEventListener(roomsListener)

            awaitClose {
                FirebaseDatabase.getInstance().reference.child("Rooms").orderByChild("time")
                    .removeEventListener(roomsListener)
            }
        }
    }

    override suspend fun sendFcmData(
        type: String,
        response: String,
        name: String,
        key: String,
        authorEmail: String,
        toUsersIDsList: List<String>
    ): Response<Any> {
        return fcmApiService.sendFcmCallData(
            CallInvitationModel(
                CallInvitationDataModel(name, type, response, key, authorEmail), toUsersIDsList
            )
        )
    }
}