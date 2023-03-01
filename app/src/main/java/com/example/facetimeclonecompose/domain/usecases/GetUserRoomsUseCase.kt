package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotVerifiedException
import javax.inject.Inject

class GetUserRoomsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(): List<RoomModel>? {
        if (!userRepository.isUserLoggedIn())
            throw UserNotFoundException()
        if (!userRepository.isUserAccountVerified())
            throw UserNotVerifiedException()
        return roomRepository.getUserRooms()
    }
}