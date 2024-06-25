package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.models.RoomTypeModel
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import javax.inject.Inject

class CreateAudioRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val launchJitsiMeetingUseCase: LaunchJitsiMeetingUseCase
) {
    suspend operator fun invoke(participantsEmails: List<String>? = null) {
        checkIsAccountValidUseCase()
        if (participantsEmails != null)
            validateParticipantsEmails(participantsEmails)
        roomRepository.createRoom(RoomTypeModel.AUDIO,participantsEmails)!!.apply {
            launchJitsiMeetingUseCase(roomId, isVideoMuted = true)
        }
    }
    private fun validateParticipantsEmails(participantsEmails: List<String>) {
        participantsEmails.forEach { participantEmail ->
            validateEmailUseCase(participantEmail)
        }
    }
}