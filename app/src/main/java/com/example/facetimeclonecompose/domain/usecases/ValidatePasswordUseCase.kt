package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.utilities.Constants.PASSWORD_PATTERN
import com.example.facetimeclonecompose.domain.utilities.InvalidPasswordException
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String) {
        if (password.isBlank())
            throw InvalidPasswordException(error = "Please enter password")
        if (!password.matches(PASSWORD_PATTERN.toRegex()))
            throw InvalidPasswordException(error = "Please enter valid password")
    }
}