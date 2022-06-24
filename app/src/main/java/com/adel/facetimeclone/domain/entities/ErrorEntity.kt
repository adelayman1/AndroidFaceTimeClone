package com.adel.facetimeclone.domain.entities

sealed class ErrorEntity{
    object GetDataError: ErrorEntity()
    object WriteDataError: ErrorEntity()
    object AuthError: ErrorEntity()
}
