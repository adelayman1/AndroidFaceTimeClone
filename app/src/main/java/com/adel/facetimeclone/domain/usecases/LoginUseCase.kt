package com.adel.facetimeclone.domain.usecases

import android.util.Patterns
import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginUseCase @Inject constructor(val userRepository: UserRepositoryImpl) {
    suspend operator fun invoke(email: String, password: String): String {
        if (email.trim().isEmpty())
            throw Exception("email is not valid")
        if (password.trim().isEmpty())
            throw Exception("password is not valid")
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            throw Exception("email is not valid")
        if (password.trim().length < 6)
            throw Exception("password is week")
        if (FirebaseAuth.getInstance().currentUser != null)
            throw Exception("you have already signed in")
        val loginWithEmailAndPasswordResult =
            userRepository.loginWithEmailAndPassword(email, password)
        return loginWithEmailAndPasswordResult.email ?: throw Exception("error when login")
    }
}
