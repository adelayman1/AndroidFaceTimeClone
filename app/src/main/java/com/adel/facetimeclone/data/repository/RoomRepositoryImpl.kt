package com.adel.facetimeclone.data.repository

import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.data.source.remote.dataSource.FcmRemoteDataSource
import com.adel.facetimeclone.data.source.remote.dataSource.RoomRemoteDataSource
import com.adel.facetimeclone.data.source.remote.requestModels.CallInvitationDataModel
import com.adel.facetimeclone.data.source.remote.requestModels.CallInvitationRequestModel
import com.adel.facetimeclone.domain.repository.RoomRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    var roomRemoteDataSource: RoomRemoteDataSource,
    var fcmRemoteDataSource: FcmRemoteDataSource
) : RoomRepository {
    override suspend fun createRoom(roomModel: RoomModel): String? {
        return roomRemoteDataSource.createRoom(roomModel)
    }

    override suspend fun changeIsUserMissedCallValue(
        roomKey: String,
        userEmail: String,
        isMissedCall: Boolean
    ): Boolean = roomRemoteDataSource.changeRoomUserMissedCallValue(
        roomKey = roomKey,
        userEmail = userEmail.replace(".", ","),
        isMissedCall = isMissedCall
    )


    @ExperimentalCoroutinesApi
    override suspend fun getAllUserCalls(userEmail: String): Flow<List<RoomModel>> =
        roomRemoteDataSource.getAllUserRooms(userEmail = userEmail.replace(".", ","))

    override suspend fun sendFcmData(
        type: String,
        response: String,
        name: String,
        key: String,
        authorEmail: String,
        toUsersIDsList: List<String>
    ): Boolean {
        val sendMessageResult = fcmRemoteDataSource.sendCallInvitation(CallInvitationRequestModel(
            CallInvitationDataModel(name, type, response, key, authorEmail), toUsersIDsList))
       return if(sendMessageResult.isSuccessful && sendMessageResult.code() == 200){
            true
        }else
            throw Exception(sendMessageResult.message())
    }
}