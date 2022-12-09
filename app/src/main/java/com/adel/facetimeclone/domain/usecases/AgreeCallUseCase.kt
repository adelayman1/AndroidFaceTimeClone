package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.repository.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AgreeCallUseCase @Inject constructor(
    var roomRepository: RoomRepository,
    var userRepository: UserRepositoryImpl,
    var externalScope: CoroutineScope
) {
    suspend operator fun invoke(roomKey: String) {
        externalScope.launch {
            val userEmail = userRepository.getUser()?.email!!
            roomRepository.changeIsUserMissedCallValue(roomKey, userEmail, false)
        }.join()
    }
}
