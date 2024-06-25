package com.example.facetimeclonecompose.domain.usecases

import android.util.Patterns
import com.example.facetimeclonecompose.domain.utilities.InvalidEmailException
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String) {
        if (email.isBlank())
            throw InvalidEmailException(error = "Please enter email")
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            throw InvalidEmailException(error = "Please enter valid email")
//        return ValidateResult()
    }
}