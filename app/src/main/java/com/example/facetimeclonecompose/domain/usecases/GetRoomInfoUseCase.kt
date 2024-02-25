package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.AccessDeniedException
import com.example.facetimeclonecompose.domain.utilities.RoomNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotVerifiedException
import javax.inject.Inject

class GetRoomInfoUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    private var userId: String? = null
    private var roomInfo: RoomModel? = null
    suspend operator fun invoke(roomId: String): RoomModel {
        checkIsAccountValidUseCase()
        roomInfo = roomRepository.getRoomInfo(roomId)
        if(!isUserHasAccess())
            throw AccessDeniedException()
        else
            return roomInfo ?: throw RoomNotFoundException()
    }

    private suspend fun isUserHasAccess(): Boolean {
        userId = userRepository.getUserID()
        return if(roomInfo != null){
            (isUserAuthorOfRoom() && isUserParticipantInRoom())
        }else
            throw RoomNotFoundException()
    }

    private fun isUserAuthorOfRoom(): Boolean {
        return roomInfo?.roomAuthor == (userId ?: throw UserNotFoundException())
    }

    private fun isUserParticipantInRoom(): Boolean {
        return roomInfo?.participants?.first { it.userId == (userId ?: throw UserNotFoundException()) } != null
    }
}