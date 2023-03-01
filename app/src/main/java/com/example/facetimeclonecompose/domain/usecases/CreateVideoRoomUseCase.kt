package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.models.RoomTypeModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotVerifiedException
import javax.inject.Inject

class CreateVideoRoomUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository,
    private val validateEmailUseCase: ValidateEmailUseCase
) {
    suspend operator fun invoke(participantsEmails: List<String>): RoomModel {
        if (!userRepository.isUserLoggedIn())
            throw UserNotFoundException()
        if (!userRepository.isUserAccountVerified())
            throw UserNotVerifiedException()
        validateParticipantsEmails(participantsEmails)
        return roomRepository.createRoom(RoomTypeModel.FACETIME, participantsEmails)!!
    }

    private fun validateParticipantsEmails(participantsEmails: List<String>) {
        participantsEmails.forEach { participantEmail ->
            validateEmail(participantEmail)
        }
    }
    private fun validateEmail(email: String) {
        val validateEmailResult = validateEmailUseCase(email)
        if (!validateEmailResult.isFieldDataValid()) throw InvalidInputTextException(
            validateEmailResult.error ?: ""
        )
    }
}