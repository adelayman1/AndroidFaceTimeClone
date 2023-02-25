package com.example.facetimeclonecompose.domain.models

data class ParticipantModel(
    val userId: String,
    val userName: String,
    val missedCall: Boolean,
)