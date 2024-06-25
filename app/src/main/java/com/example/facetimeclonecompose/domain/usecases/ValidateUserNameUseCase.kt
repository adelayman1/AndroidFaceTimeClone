package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.utilities.Constants.MINIMUM_USER_NAME_LENGTH
import com.example.facetimeclonecompose.domain.utilities.InvalidNameException
import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor() {
    operator fun invoke(name: String) {
        if (name.isBlank())
            throw InvalidNameException(error = "cannot be empty")
        if (name.length<MINIMUM_USER_NAME_LENGTH)
            throw InvalidNameException(error = "Please enter a valid name")
    }

}