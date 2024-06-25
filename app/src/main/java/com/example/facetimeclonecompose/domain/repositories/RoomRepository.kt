package com.example.facetimeclonecompose.domain.repositories

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.models.RoomTypeModel

interface RoomRepository {
    suspend fun createRoom(roomType: RoomTypeModel, participantsEmails: List<String>? = null): RoomModel?

    suspend fun getRoomInfo(roomId: String): RoomModel?

    suspend fun getUserRooms(): List<RoomModel>?

    suspend fun joinRoom(roomId: String): RoomModel?

    suspend fun deleteRoom(roomId: String)

    fun launchMeeting(roomId: String, userName: String, isVideoMuted: Boolean)
}