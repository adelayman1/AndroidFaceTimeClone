package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.ValidateResult
import com.example.facetimeclonecompose.domain.utilities.Constants.PASSWORD_PATTERN
import javax.inject.Inject

class ValidateConfirmPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String,confirmPassword:String): ValidateResult {
        if (confirmPassword.isBlank())
            return ValidateResult(error = "Please enter password")
        if (!confirmPassword.matches(PASSWORD_PATTERN.toRegex()))
            return ValidateResult(error = "Please enter valid password")
        if (confirmPassword!=password)
            return ValidateResult(error = "Passwords are not the same")
        return ValidateResult()
    }
}