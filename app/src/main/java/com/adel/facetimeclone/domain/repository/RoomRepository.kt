package com.adel.facetimeclone.domain.repository

import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.domain.entities.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RoomRepository {
    suspend fun createRoom(roomModel: RoomModel): Result<String>
    suspend fun getAllUserCalls(userEmail: String): Flow<Result<List<RoomModel>>>
    suspend fun sendFcmData(
        type: String,
        response: String,
        name: String,
        key: String,
        authorEmail: String,
        toUsersIDsList: List<String>
    ): Response<Any>
    suspend fun changeIsUserMissedCallValue(roomKey:String,userEmail:String,value: Boolean): Result<Any>
}