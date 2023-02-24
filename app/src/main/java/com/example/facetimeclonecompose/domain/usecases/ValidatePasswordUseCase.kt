package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.ValidateResult
import com.example.facetimeclonecompose.domain.utilities.Constants.PASSWORD_PATTERN
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): ValidateResult {
        if (password.isBlank())
            return ValidateResult(error = "Please enter password")
        if (!password.matches(PASSWORD_PATTERN.toRegex()))
            return ValidateResult(error = "Please enter valid password")
        return ValidateResult()
    }
}