package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.ValidateResult
import javax.inject.Inject

class ValidateOtpCodeUseCase @Inject constructor() {
    operator fun invoke(otpCode: Int): ValidateResult {
        if (otpCode.toString().length != 4)
            return ValidateResult(error = "Code is very short")
        if (isAllCharsSame(otpCode))
            return ValidateResult(error = "Code is not valid")
        return ValidateResult()
    }
    private fun isAllCharsSame(otpCode: Int):Boolean{
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