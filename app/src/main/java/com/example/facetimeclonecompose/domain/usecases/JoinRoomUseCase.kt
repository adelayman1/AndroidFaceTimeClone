package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.RoomNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotVerifiedException
import javax.inject.Inject

class JoinRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    suspend operator fun invoke(roomId: String): RoomModel {
        checkIsAccountValidUseCase()
        return roomRepository.joinRoom(roomId) ?: throw RoomNotFoundException()
    }
}