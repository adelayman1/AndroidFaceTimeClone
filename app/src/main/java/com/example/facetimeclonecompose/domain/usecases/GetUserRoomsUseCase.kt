package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.Constants.LINK_ROOM_TYPE
import javax.inject.Inject

class GetUserRoomsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
    ,private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    suspend operator fun invoke(): List<RoomModel>? {
        checkIsAccountValidUseCase()
        var userRooms = roomRepository.getUserRooms()
        userRooms = userRooms?.map { room->
            when {
                room.isLinkRoom() -> room
                room.isRoomCreatedByMe() -> room.updateRoomTitleForCreator()
                else -> room.updateRoomTitleForParticipant()
            }
        }
        return userRooms
    }

    private fun RoomModel.isLinkRoom(): Boolean {
        return this.roomType.toLowerCase() == LINK_ROOM_TYPE
    }

    private fun RoomModel.updateRoomTitleForCreator(): RoomModel {
        return if (this.isRoomHasParticipants()) {
            val randomParticipantName = this.randomParticipantName()
            this.copy(roomTitle = if (this.isHasOneParticipant()) randomParticipantName else "$randomParticipantName and ${this.participantsNumber()} others")
        } else {
            this
        }
    }

    private suspend fun RoomModel.updateRoomTitleForParticipant(): RoomModel {
        return this.copy(roomTitle = userRepository.getUserProfileDataById(this.roomAuthor).userName, isMissed = this.isMissedCall())
    }

    private fun RoomModel.randomParticipantName(): String {
        val randomIndex = (0..this.participantsNumber()).random()
        return this.participants!![randomIndex].userName
    }

    private fun RoomModel.participantsNumber(): Int {
        return (participants?.size ?: 0) - 1
    }

    private fun RoomModel.isHasOneParticipant(): Boolean {
        return this.participants?.size == 1
    }

    private suspend fun RoomModel.isRoomCreatedByMe(): Boolean {
        return this.roomAuthor == userRepository.getUserID()
    }

    private fun RoomModel.isRoomHasParticipants(): Boolean {
        return !participants.isNullOrEmpty()
    }

    private suspend fun RoomModel.isMissedCall(): Boolean {
        val userId = userRepository.getUserID()
        return participants?.find { it.userId == userId}?.missedCall ?: false
    }
}