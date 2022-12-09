package com.adel.facetimeclone.domain.entities

sealed class Result<out T> {
    data class Success<out T>(val data:T) : Result<T>()
//    data class Error<out T>(val error: ErrorEntity,val msg:String) : Result<T>()
    class Loading <out T> : Result<T>()
    class Null <out T> : Result<T>()
}