package com.adel.facetimeclone.domain.repository

interface AuthRepository {

    suspend fun getMyToken(): String
}
