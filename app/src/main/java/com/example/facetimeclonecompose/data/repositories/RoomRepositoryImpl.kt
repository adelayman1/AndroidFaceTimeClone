package com.example.facetimeclonecompose.data.repositories

import com.example.facetimeclonecompose.data.sources.remote.dataSources.RoomsRemoteDataSource
import com.example.facetimeclonecompose.data.sources.remote.requestModels.CreateRoomRequestModel
import com.example.facetimeclonecompose.data.utilities.makeRequestAndHandleErrors
import com.example.facetimeclonecompose.domain.models.ParticipantModel
import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.models.RoomTypeModel
import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.models.toRoomTypeEnumRequestModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomsRemoteDataSource: RoomsRemoteDataSource,
    private val externalScope: CoroutineScope
) : RoomRepository {
    override suspend fun createRoom(roomType: RoomTypeModel, participantsEmails: List<String>?): RoomModel? {
        return makeRequestAndHandleErrors {
            roomsRemoteDataSource.createNewRoom(
                CreateRoomRequestModel(
                    roomType = roomType.toRoomTypeEnumRequestModel(),
                    participantsEmails = participantsEmails
                )
            )
        }.let {
            it?.toRoomModel()
        }
    }

    override suspend fun getRoomInfo(roomId: String): RoomModel? {
        return makeRequestAndHandleErrors {
            roomsRemoteDataSource.getRoomInfo(roomId)
        }.let {
            it?.toRoomModel()
        }
    }

    override suspend fun getUserRooms(): List<RoomModel>? {
        return makeRequestAndHandleErrors {
            roomsRemoteDataSource.getUserRooms()
        }.let {
            it?.map {
                it.toRoomModel()
            }
        }
    }

    override suspend fun joinRoom(roomId: String): RoomModel? {
        return makeRequestAndHandleErrors {
            roomsRemoteDataSource.joinRoom(roomId)
        }.let {
            it?.toRoomModel()
        }
    }

    override suspend fun deleteRoom(roomId: String) {
        externalScope.launch {
            makeRequestAndHandleErrors {
                roomsRemoteDataSource.deleteRoom(roomId)
            }
        }.join()
    }
}