package com.example.facetimeclonecompose.domain.usecases

import android.util.Patterns
import com.example.facetimeclonecompose.domain.models.ValidateResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): ValidateResult {
        if (email.isBlank())
            return ValidateResult(error = "Please enter email")
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return ValidateResult(error = "Please enter valid email")
        return ValidateResult()
    }
}