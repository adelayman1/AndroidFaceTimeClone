package com.adel.facetimeclone.data.source.remote.dataSource

import com.adel.facetimeclone.data.source.remote.endPoints.FCMCallApiService
import com.adel.facetimeclone.data.source.remote.requestModels.CallInvitationRequestModel
import retrofit2.Response
import javax.inject.Inject

class FcmRemoteDataSource @Inject constructor(var fcmCallApiService: FCMCallApiService){

    suspend fun sendCallInvitation(
        callInvitationRequestModel: CallInvitationRequestModel
    ): Response<Any> {
        return fcmCallApiService.sendFcmCallData(callInvitationRequestModel)
    }
}