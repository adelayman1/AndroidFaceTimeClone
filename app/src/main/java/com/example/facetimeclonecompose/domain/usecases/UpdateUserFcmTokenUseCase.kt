package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.repositories.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton    //To inject it into FCM service class
class UpdateUserFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    // TODO("CLEAN CODE CHECK")
    suspend operator fun invoke(token: String) {
        return userRepository.updateUserFcmToken(token)
    }
}