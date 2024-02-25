package com.example.facetimeclonecompose.domain.usecases

import com.example.facetimeclonecompose.domain.repositories.UserRepository
import com.example.facetimeclonecompose.domain.utilities.UserNotFoundException
import javax.inject.Inject

class DeleteUserAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val checkIsAccountValidUseCase: CheckIsAccountValidUseCase
) {
    suspend operator fun invoke() {
        checkIsAccountValidUseCase()
        userRepository.deleteUserAccount()
    }
}