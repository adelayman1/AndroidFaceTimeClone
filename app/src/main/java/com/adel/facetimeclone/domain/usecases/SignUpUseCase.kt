package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    val userRepository: UserRepositoryImpl,
    val updateTokenUseCase: UpdateTokenUseCase
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String
    ): String {
        if (email.trim().isEmpty())
            throw Exception("email is not valid")
        if (password.trim().isEmpty())
            throw Exception("password is not valid")
        if (name.trim().isEmpty())
            throw Exception("name is not valid")

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            throw Exception("email is not valid")
        if (password.trim().length < 6)
            throw Exception("password is not valid")
        if (FirebaseAuth.getInstance().currentUser != null)
            throw java.lang.Exception("you have already signed in")
        val createAccountWithEmailAndPasswordResult =
            userRepository.createAccountWithEmailAndPassword(email, password)
        val updateUserDataResult = userRepository.updateUserData(email, name)
        val getUserTokenResult = userRepository.getUserToken()
        val updateUserTokenResult = updateTokenUseCase()
        return createAccountWithEmailAndPasswordResult.email?:throw Exception("email is empty")
    }
}
