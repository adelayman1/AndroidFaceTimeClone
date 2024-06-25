package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.UserModel
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.UserLoggedInException
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val confirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase
) {
    suspend operator fun invoke(userName: String, email: String, password: String,confirmPassword:String): UserModel {
        if (userRepository.isUserLoggedIn())
            throw UserLoggedInException()
        validateFields(userName, email, password,confirmPassword)
        return userRepository.createNewAccount(name = userName, email = email, password = password)!!
    }

    private fun validateFields(userName: String, email: String, password: String,confirmPassword: String) {
        validateUserNameUseCase(userName)
        validateEmailUseCase(email)
        validatePasswordUseCase(password)
        confirmPasswordUseCase(password = password, confirmPassword = confirmPassword)
    }
}