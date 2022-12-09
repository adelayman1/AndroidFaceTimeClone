package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.domain.repository.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class KickOutAllRoomUsersUseCase @Inject constructor(
    val roomRepository: RoomRepository,
    var externalScope: CoroutineScope
) {
    suspend operator fun invoke(roomKey: String, users: List<String>) {
        externalScope.launch {
            roomRepository.sendFcmData("response", "cancel", "none", roomKey, "none", users)
        }.join()
    }
}
