package com.example.facetimeclonecompose.domain.models

data class ValidateResult(
    var error: String? = null
){
    fun isFieldDataValid() = error == null
}