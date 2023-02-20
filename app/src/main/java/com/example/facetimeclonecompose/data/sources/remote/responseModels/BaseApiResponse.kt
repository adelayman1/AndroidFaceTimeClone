package com.example.facetimeclonecompose.data.sources.remote.responseModels

import kotlinx.serialization.Serializable

@Serializable
class BaseApiResponse<T>(var status: Boolean, var message: String, var data: T?=null)