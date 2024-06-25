package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.UserLoggedInException
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val updateUserFcmTokenUseCase: UpdateUserFcmTokenUseCase
) {
    suspend operator fun invoke(email: String, password: String): UserModel {
        if (userRepository.isUserLoggedIn())
            throw UserLoggedInException()
        validateFields(email, password)
        return userRepository.login(email = email, password = password)?.also {
            updateUserFcmTokenUseCase(userRepository.getFCMToken())
        } ?: throw UserNotFoundException()
    }

    private fun validateFields(email: String, password: String) {
        validateEmailUseCase(email)
        validatePasswordUseCase(password)
    }
}