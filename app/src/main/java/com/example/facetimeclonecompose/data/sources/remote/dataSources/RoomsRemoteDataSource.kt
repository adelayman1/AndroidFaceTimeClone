package com.example.facetimeclonecompose.data.sources.remote.dataSources

import com.example.facetimeclonecompose.data.sources.remote.requestModels.CreateRoomRequestModel
import com.example.facetimeclonecompose.data.sources.remote.responseModels.BaseApiResponse
import com.example.facetimeclonecompose.data.sources.remote.responseModels.RoomResponseModel
import com.example.facetimeclonecompose.data.utilities.Constants
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomsRemoteDataSource @Inject constructor(private val httpClient: HttpClient) {
    suspend fun createNewRoom(createRoomRequestModel: CreateRoomRequestModel): BaseApiResponse<RoomResponseModel> =
        withContext(Dispatchers.IO) {
            httpClient.post {
                url("${Constants.BASE_URL}/${Constants.ROOMS_ENDPOINT}")
                header("No-Authorization", "false")
                contentType(ContentType.Application.Json)
                body = createRoomRequestModel
            }
        }

    suspend fun joinRoom(roomId: String): BaseApiResponse<RoomResponseModel> =
        withContext(Dispatchers.IO) {
            httpClient.post {
                url("${Constants.BASE_URL}/${Constants.ROOMS_ENDPOINT}/$roomId/join")
                header("No-Authorization", "false")
                contentType(ContentType.Application.Json)
            }
        }

    suspend fun deleteRoom(roomId: String): BaseApiResponse<String> =
        withContext(Dispatchers.IO) {
            httpClient.delete {
                url("${Constants.BASE_URL}/${Constants.ROOMS_ENDPOINT}/$roomId")
                header("No-Authorization", "false")
                contentType(ContentType.Application.Json)
            }
        }

    suspend fun getRoomInfo(roomId: String): BaseApiResponse<RoomResponseModel> =
        withContext(Dispatchers.IO) {
            httpClient.get {
                url("${Constants.BASE_URL}/${Constants.ROOMS_ENDPOINT}/$roomId")
                header("No-Authorization", "false")
                contentType(ContentType.Application.Json)
            }
        }

    suspend fun getUserRooms(): BaseApiResponse<List<RoomResponseModel>> =
        withContext(Dispatchers.IO) {
            httpClient.get {
                url("${Constants.BASE_URL}/${Constants.ROOMS_ENDPOINT}")
                header("No-Authorization", "false")
                contentType(ContentType.Application.Json)
            }
        }
}