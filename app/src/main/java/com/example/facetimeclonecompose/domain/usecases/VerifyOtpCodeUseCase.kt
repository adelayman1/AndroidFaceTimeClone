package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.data.utilities.ApiException
import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import javax.inject.Inject

class VerifyOtpCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validOtpCodeUseCase: ValidateOtpCodeUseCase,
    private val updateUserFcmTokenUseCase: UpdateUserFcmTokenUseCase
) {
    suspend operator fun invoke(otpCode: Int): UserModel {
        validOtpCodeUseCase(otpCode)
        try {
            val userModel = userRepository.verifyOtpCode(otpCode).also {
                updateUserFcmTokenUseCase(userRepository.getFCMToken())
            }
            return userModel
        } catch (e: ApiException) {
            throw InvalidInputTextException(e.message.toString())
        }
    }
}