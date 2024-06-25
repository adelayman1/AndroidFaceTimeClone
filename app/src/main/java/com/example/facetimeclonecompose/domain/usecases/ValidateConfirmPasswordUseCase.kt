package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.utilities.Constants.PASSWORD_PATTERN
import com.example.facetimeclonecompose.domain.utilities.InvalidConfirmPasswordException
import javax.inject.Inject

class ValidateConfirmPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String,confirmPassword:String) {
        if (confirmPassword.isBlank())
            throw InvalidConfirmPasswordException(error = "cannot be empty")
        if (!confirmPassword.matches(PASSWORD_PATTERN.toRegex()))
            throw InvalidConfirmPasswordException(error = "Please enter a valid password")
        if (confirmPassword!=password)
            throw InvalidConfirmPasswordException(error = "Passwords are not the same")
    }
}