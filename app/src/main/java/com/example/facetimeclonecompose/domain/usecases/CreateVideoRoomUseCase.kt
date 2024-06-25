package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomTypeModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import javax.inject.Inject

class CreateVideoRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val launchJitsiMeetingUseCase: LaunchJitsiMeetingUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    suspend operator fun invoke(participantsEmails: List<String>? = null) {
        checkIsAccountValidUseCase()
        if (participantsEmails != null)
            validateParticipantsEmails(participantsEmails)
        roomRepository.createRoom(RoomTypeModel.FACETIME, participantsEmails)!!.apply {
            launchJitsiMeetingUseCase(roomId, isVideoMuted = false)
        }
    }

    private fun validateParticipantsEmails(participantsEmails: List<String>) {
        participantsEmails.forEach { participantEmail ->
            validateEmailUseCase(participantEmail)
        }
    }
}