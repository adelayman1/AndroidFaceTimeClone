package com.adel.facetimeclone.data.api

import com.adel.facetimeclone.data.model.CallInvitationModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmApiService {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key="
    )
    @POST("fcm/send")
    suspend fun sendFcmCallData(@Body invitationModel: CallInvitationModel): Response<Any>
}
