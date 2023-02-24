package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import com.example.facetimeclonecompose.domain.utilities.UserLoggedInException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {
    suspend operator fun invoke(email: String, password: String): UserModel {
        if (!userRepository.isUserLoggedIn())
            throw UserLoggedInException()
        validateFields(email, password)
        return userRepository.login(email = email, password = password)!!
    }

    private suspend fun validateFields(email: String, password: String) {
        val validateEmailResult = validateEmailUseCase(email)
        if (!validateEmailResult.isFieldDataValid())
            throw InvalidInputTextException(validateEmailResult.error ?: "")

        val validatePasswordResult = validatePasswordUseCase(password)
        if (!validatePasswordResult.isFieldDataValid())
            throw InvalidInputTextException(validatePasswordResult.error ?: "")
    }
}