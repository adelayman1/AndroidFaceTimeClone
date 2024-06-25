package com.example.facetimeclonecompose.data.repositories

import android.content.Context
import com.example.facetimeclonecompose.data.sources.remote.dataSources.RoomsRemoteDataSource
import com.example.facetimeclonecompose.data.sources.remote.requestModels.CreateRoomRequestModel
import com.example.facetimeclonecompose.data.sources.remote.requestModels.JoinRoomRequestModel
import com.example.facetimeclonecompose.data.utilities.Constants.JITSI_MEET_SERVER
import com.example.facetimeclonecompose.data.utilities.makeRequestAndHandleErrors
import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.models.RoomTypeModel
import com.example.facetimeclonecompose.domain.models.toRoomTypeEnumRequestModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomsRemoteDataSource: RoomsRemoteDataSource,
    private val externalScope: CoroutineScope,
    @ApplicationContext private val context: Context
) : RoomRepository {
    override suspend fun createRoom(
        roomType: RoomTypeModel,
        participantsEmails: List<String>?
    ): RoomModel? {
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
            it?.map { room ->
                room.toRoomModel()
            }
        }
    }

    override suspend fun joinRoom(roomId: String): RoomModel? {
        return makeRequestAndHandleErrors {
            roomsRemoteDataSource.joinRoom(JoinRoomRequestModel(roomId))
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

    override fun launchMeeting(roomId: String, userName: String, isVideoMuted: Boolean) {
        val userInfo = JitsiMeetUserInfo().apply { displayName = userName }
        val conferenceOptions = JitsiMeetConferenceOptions.Builder()
            .setRoom("$JITSI_MEET_SERVER/$roomId")
            .setAudioMuted(false)
            .setUserInfo(userInfo)
            .setVideoMuted(isVideoMuted)
            .setFeatureFlag("prejoinpage.enabled", false)
            .build()
        JitsiMeetActivity.launch(context, conferenceOptions)
    }
}