package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import com.example.facetimeclonecompose.domain.utilities.UserLoggedInException
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase
) {
    suspend operator fun invoke(userName: String, email: String, password: String): UserModel {
        if (userRepository.isUserLoggedIn())
            throw UserLoggedInException()
        validateFields(userName, email, password)
        return userRepository.createNewAccount(name = userName, email = email, password = password)!!
    }

    private suspend fun validateFields(userName: String, email: String, password: String) {
        validateUserName(userName)
        validateEmail(email)
        validatePassword(password)
    }

    private fun validateUserName(userName: String) {
        val validateUserNameResult = validateUserNameUseCase(userName)
        if (!validateUserNameResult.isFieldDataValid()) throw InvalidInputTextException(
            validateUserNameResult.error ?: ""
        )
    }

    private fun validateEmail(email: String) {
        val validateEmailResult = validateEmailUseCase(email)
        if (!validateEmailResult.isFieldDataValid()) throw InvalidInputTextException(
            validateEmailResult.error ?: ""
        )
    }

    private fun validatePassword(password: String) {
        val validatePasswordResult = validatePasswordUseCase(password)
        if (!validatePasswordResult.isFieldDataValid()) throw InvalidInputTextException(
            validatePasswordResult.error ?: ""
        )
    }
}