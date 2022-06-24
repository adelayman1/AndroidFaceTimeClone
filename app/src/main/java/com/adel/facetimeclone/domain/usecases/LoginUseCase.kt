package com.adel.facetimeclone.domain.usecases

import com.adel.facetimeclone.data.repository.UserRepositoryImpl
import com.adel.facetimeclone.domain.entities.ErrorEntity
import com.adel.facetimeclone.domain.entities.Result
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginUseCase @Inject constructor(val userRepository: UserRepositoryImpl){
    val emailPattern:String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    suspend operator fun invoke(email:String,password:String):Result<String>{
        if(email.trim().isEmpty())
            return Result.Error(ErrorEntity.GetDataError,"null data")
        if(password.trim().isEmpty())
            return Result.Error(ErrorEntity.GetDataError,"null data")
        if(email.trim().matches(Regex(emailPattern)))
            return Result.Error(ErrorEntity.GetDataError,"null data")
        if(password.trim().length<6)
            return Result.Error(ErrorEntity.GetDataError,"null data")
        if(FirebaseAuth.getInstance().currentUser != null)
            return Result.Error(ErrorEntity.GetDataError,"null data")
        val loginWithEmailAndPasswordResult = userRepository.loginWithEmailAndPassword(email,password)
       return when(loginWithEmailAndPasswordResult){
            is Result.Success ->{
                Result.Success(loginWithEmailAndPasswordResult.data.email!!)
            }
           is Result.Error -> Result.Error(loginWithEmailAndPasswordResult.error,loginWithEmailAndPasswordResult.msg)
            else -> TODO()
        }
    }
}
