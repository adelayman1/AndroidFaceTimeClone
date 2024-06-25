package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.utilities.RoomNotFoundException
import javax.inject.Inject

class JoinRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase,
    private val launchJitsiMeetingUseCase: LaunchJitsiMeetingUseCase
) {
    suspend operator fun invoke(roomId: String) {
        checkIsAccountValidUseCase()
        roomRepository.joinRoom(roomId)?.let {
            launchJitsiMeetingUseCase(roomId, isVideoMuted = true)
        } ?: throw RoomNotFoundException()
    }
}