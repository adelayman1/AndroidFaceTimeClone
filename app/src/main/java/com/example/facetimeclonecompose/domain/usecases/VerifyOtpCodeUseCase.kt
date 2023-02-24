package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.data.utilities.ApiException
import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import javax.inject.Inject

class VerifyOtpCodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validOtpCodeUseCase: ValidateOtpCodeUseCase
) {
    suspend operator fun invoke(otpCode: Int): UserModel {
        val validateOtpCodeResult = validOtpCodeUseCase(otpCode)
        if (!validateOtpCodeResult.isFieldDataValid())
            throw InvalidInputTextException(validateOtpCodeResult.error ?: "")
        try {
            return userRepository.verifyOtpCode(otpCode)
        } catch (e: ApiException) {
            throw InvalidInputTextException(e.message.toString())
        }
    }
}