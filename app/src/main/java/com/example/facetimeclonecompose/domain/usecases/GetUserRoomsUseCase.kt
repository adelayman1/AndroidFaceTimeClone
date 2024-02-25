package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserRoomsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
    ,private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    suspend operator fun invoke(): List<RoomModel>? {
        checkIsAccountValidUseCase()
        val userRooms = roomRepository.getUserRooms()
        userRooms?.map { room->
            if (!room.isLinkRoom()) {
                if (room.isRoomCreatedByMe()) {
                    if (room.isRoomHasParticipants()) {
                        val randomParticipantName = room.randomParticipantName()
                        room.copy(roomTitle = if (room.isHasOneParticipant()) randomParticipantName else (randomParticipantName + " and ${(room.participantsNumber())} others"))
                    }else
                        room
                } else {
                    room.copy(roomTitle = userRepository.getUserProfileDataById(room.roomAuthor).userName)
                }
            } else {
                room
            }
        }
        return userRooms
    }

    private fun RoomModel.isLinkRoom(): Boolean {
        return this.roomType == "link"
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
}