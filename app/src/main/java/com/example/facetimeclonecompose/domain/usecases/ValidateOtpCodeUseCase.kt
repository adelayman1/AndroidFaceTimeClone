package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.utilities.InvalidOtpException
import javax.inject.Inject

class ValidateOtpCodeUseCase @Inject constructor() {
    operator fun invoke(otpCode: Int) {
        if (otpCode.toString().length != 4)
            throw InvalidOtpException(error = "Code is very short")
        if (areAllDigitsIdentical(otpCode))
            throw InvalidOtpException(error = "Code is not valid")
    }
    private fun areAllDigitsIdentical(otpCode: Int):Boolean{
        return (
            getCharByIndex(otpCode, 0) == getCharByIndex(otpCode, 1)
            && getCharByIndex(otpCode, 1) == getCharByIndex(otpCode, 2)
            && getCharByIndex(otpCode, 2) == getCharByIndex(otpCode, 3)
        )
    }
    private fun getCharByIndex(otpCode: Int, charIndex: Int): Char {
        return otpCode.toString().toCharArray()[charIndex]
    }
}