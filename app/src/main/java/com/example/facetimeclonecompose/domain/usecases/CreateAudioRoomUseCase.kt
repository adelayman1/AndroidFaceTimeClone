package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomModel
import com.example.facetimeclonecompose.domain.models.RoomTypeModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.InvalidInputTextException
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import com.example.facetimeclonecompose.domain.utilities.UserNotVerifiedException
import com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates.ParticipantsInputFieldUiState
import javax.inject.Inject

class CreateAudioRoomUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    suspend operator fun invoke(participantsEmails: List<String>? = null): RoomModel {
        checkIsAccountValidUseCase()
        if (participantsEmails != null)
            validateParticipantsEmails(participantsEmails)
        return roomRepository.createRoom(RoomTypeModel.AUDIO,participantsEmails)!!
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