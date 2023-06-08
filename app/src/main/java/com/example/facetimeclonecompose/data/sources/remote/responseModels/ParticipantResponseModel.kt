package com.example.facetimeclonecompose.data.sources.remote.responseModels

import com.example.facetimeclonecompose.domain.models.ParticipantModel
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantResponseModel(
    val userId: String,
    val userEmail: String,
    val userName: String,
    val missedCall: Boolean,
) {
    fun toParticipantModel() =
        ParticipantModel(
            userId = userId,
            userName = userName,
            missedCall = missedCall
        )
}