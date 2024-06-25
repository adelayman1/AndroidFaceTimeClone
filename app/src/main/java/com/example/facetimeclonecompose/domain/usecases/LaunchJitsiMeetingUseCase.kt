package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchJitsiMeetingUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val getUserProfileUseCase: GetUserProfileUseCase
) {
    suspend operator fun invoke(roomId: String, isVideoMuted: Boolean) = withContext(Dispatchers.Main){
        roomRepository.launchMeeting(roomId, getUserProfileUseCase().userName, isVideoMuted)
    }
}