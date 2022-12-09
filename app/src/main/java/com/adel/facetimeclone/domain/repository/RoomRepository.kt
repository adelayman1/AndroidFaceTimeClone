package com.adel.facetimeclone.domain.repository

import com.adel.facetimeclone.data.model.RoomModel
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun createRoom(roomModel: RoomModel): String?
    suspend fun getAllUserCalls(userEmail: String): Flow<List<RoomModel>>
    suspend fun sendFcmData(
        type: String,
        response: String,
        name: String,
        key: String,
        authorEmail: String,
        toUsersIDsList: List<String>
    ): Boolean

    suspend fun changeIsUserMissedCallValue(
        roomKey: String,
        userEmail: String,
        isMissedCall: Boolean
    ): Boolean
}