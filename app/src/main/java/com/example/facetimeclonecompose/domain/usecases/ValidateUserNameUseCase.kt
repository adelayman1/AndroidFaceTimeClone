package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.ValidateResult
import com.example.facetimeclonecompose.domain.utilities.Constants.MINIMUM_USER_NAME_LENGTH
import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor() {
    operator fun invoke(name: String): ValidateResult {
        if (name.isBlank())
            return ValidateResult(error = "Please enter name")
        if (name.length<MINIMUM_USER_NAME_LENGTH)
            return ValidateResult(error = "Please enter valid name")
        return ValidateResult()
    }

}