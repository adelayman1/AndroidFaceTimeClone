package com.adel.facetimeclone.data.api

import com.adel.facetimeclone.data.model.CallInvitationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmApiService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=AAAAfOUKvh4:APA91bFnBpXUB2Qr0Th6nLVFQXMdb1n5-WyeBfrnCDYEc3xeQJ30WCm7uE5GreNhRjpaIDY6-W_ChZ91ncVbD_jHnZHmHjoUltKt_rVdkt1vkcaZX4WBsLVkK_vNRyJHL9q77OIY_U3R"
    )
    @POST("fcm/send")
    suspend fun sendFcmCallData(@Body invitationModel: CallInvitationModel): Response<Any>
}