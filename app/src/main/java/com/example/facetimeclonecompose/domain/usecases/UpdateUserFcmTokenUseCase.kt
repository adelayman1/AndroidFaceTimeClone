package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.RoomNotFoundException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton    //To inject it into FCM service class
class UpdateUserFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    // TODO("CLEAN CODE CHECK")
    suspend operator fun invoke(token: String) {
        checkIsAccountValidUseCase()
        return userRepository.updateUserFcmToken(token)
    }
}