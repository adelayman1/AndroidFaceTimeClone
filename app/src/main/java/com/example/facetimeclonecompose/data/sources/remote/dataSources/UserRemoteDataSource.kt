package com.example.facetimeclonecompose.data.sources.remote.dataSources

import com.example.facetimeclonecompose.data.sources.remote.requestModels.EditFcmTokenRequestModel
import com.example.facetimeclonecompose.data.sources.remote.requestModels.LoginRequestModel
import com.example.facetimeclonecompose.data.sources.remote.requestModels.RegisterRequestModel
import com.example.facetimeclonecompose.data.sources.remote.responseModels.BaseApiResponse
import com.example.facetimeclonecompose.data.sources.remote.responseModels.UserResponseModel
import com.example.facetimeclonecompose.data.utilities.Constants.BASE_URL
import com.example.facetimeclonecompose.data.utilities.Constants.USER_ENDPOINT
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(var httpClient: HttpClient) {
    suspend fun loginWithEmailAndPassword(loginRequestModel: LoginRequestModel): BaseApiResponse<UserResponseModel> =
        withContext(Dispatchers.IO) {
            httpClient.post {
                url("$BASE_URL/$USER_ENDPOINT/login")
                header("No-Authorization","true")
                contentType(ContentType.Application.Json)
                body = loginRequestModel
            }
        }
    suspend fun createNewAccount(registerRequestModel: RegisterRequestModel): BaseApiResponse<UserResponseModel> =
        withContext(Dispatchers.IO) {
            httpClient.post {
                url("$BASE_URL/$USER_ENDPOINT/register")
                header("No-Authorization","true")
                contentType(ContentType.Application.Json)
                body = registerRequestModel
            }
        }
    //TODO(AUTH KEY REQUIRED)
    suspend fun getProfileData(): BaseApiResponse<UserResponseModel> =
        withContext(Dispatchers.IO) {
            httpClient.get {
                url("$BASE_URL/$USER_ENDPOINT/profile")
                header("No-Authorization","false")
                contentType(ContentType.Application.Json)
            }
        }
    suspend fun sendVerificationCode(): BaseApiResponse<Any> =
        withContext(Dispatchers.IO) {
            httpClient.get {
                url("$BASE_URL/$USER_ENDPOINT/send-email-code")
                header("No-Authorization","false")
                contentType(ContentType.Application.Json)
            }
        }
    suspend fun verifyOtpCode(otpCode:Int): BaseApiResponse<UserResponseModel> =
        withContext(Dispatchers.IO) {
            httpClient.post {
                url("$BASE_URL/$USER_ENDPOINT/verify-code")
                parameter("otp_code",otpCode)
                header("No-Authorization","false")
                contentType(ContentType.Application.Json)
            }
        }
    suspend fun deleteAccount(): BaseApiResponse<Any> =
        withContext(Dispatchers.IO) {
            httpClient.delete {
                url("$BASE_URL/$USER_ENDPOINT")
                header("No-Authorization","false")
                contentType(ContentType.Application.Json)
            }
        }
    suspend fun editUserFcmToken(editFcmTokenRequestModel: EditFcmTokenRequestModel): BaseApiResponse<UserResponseModel> =
        withContext(Dispatchers.IO) {
            httpClient.patch {
                url("$BASE_URL/$USER_ENDPOINT/fcm-token")
                header("No-Authorization","false")
                contentType(ContentType.Application.Json)
                body = editFcmTokenRequestModel
            }
        }
    suspend fun getUserFcmToken(): BaseApiResponse<UserResponseModel> =
        withContext(Dispatchers.IO) {
            httpClient.get {
                url("$BASE_URL/$USER_ENDPOINT/fcm-token")
                header("No-Authorization","false")
                contentType(ContentType.Application.Json)
            }
        }
}