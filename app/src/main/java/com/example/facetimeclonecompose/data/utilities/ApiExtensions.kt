package com.example.facetimeclonecompose.data.utilities

import com.example.facetimeclonecompose.data.sources.remote.responseModels.BaseApiResponse
import io.ktor.client.features.ClientRequestException
import io.ktor.client.statement.readText
import io.ktor.utils.io.charsets.Charset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

suspend fun <T> makeRequestAndHandleErrors(block: suspend () -> BaseApiResponse<T>): T? {
    return try {
        withContext(Dispatchers.IO) {
            block().also {
                if (!it.isDataHasGotSuccessfully()) {
                    throw Exception(it.message)
                }
            }.data
        }
    } catch (e: ClientRequestException) {
        throw e.handleError()
    }
}

suspend fun ClientRequestException.handleError(): Exception {
    val content = response.readText(Charset.defaultCharset())
    return Exception(Json.decodeFromString<BaseApiResponse<Any>>(content).message)
}

fun <T> BaseApiResponse<T>.isDataHasGotSuccessfully() = status && message.isNotBlank()