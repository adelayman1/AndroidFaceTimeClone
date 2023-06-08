package com.example.facetimeclonecompose.data.di.modules

import com.example.facetimeclonecompose.data.repositories.RoomRepositoryImpl
import com.example.facetimeclonecompose.data.repositories.UserRepositoryImpl
import com.example.facetimeclonecompose.domain.repositories.RoomRepository
import com.example.facetimeclonecompose.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository {
        return roomRepositoryImpl
    }

    @Singleton
    @Provides
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository {
        return userRepositoryImpl
    }
}