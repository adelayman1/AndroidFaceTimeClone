package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.repository.RoomRepository
import javax.inject.Inject

class SendCallInvitationUseCase @Inject constructor(
    val roomRepository: RoomRepository,
    val userRepository: UserRepositoryImpl,
    private val getUsersTokenUseCase: GetUsersTokenUseCase
) {
    suspend operator fun invoke(
        name: String,
        roomKey: String,
        toUsersList: List<String>,
        roomType: String
    ) {
        val getUsersTokenResult = getUsersTokenUseCase.invoke(toUsersList)
            val userEmail = userRepository.getUser()?.email?:throw Exception("Email not found")
            roomRepository.sendFcmData(
                "call",
                roomType,
                name,
                roomKey,
                userEmail,
                getUsersTokenResult
            )
    }
}