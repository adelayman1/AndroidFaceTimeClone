package com.example.facetimeclonecompose.data.sources.remote.responseModels

data class ParticipantResponseModel(
    val userId: String,
    val userEmail: String,
    val userName: String,
    val missedCall: Boolean,
)