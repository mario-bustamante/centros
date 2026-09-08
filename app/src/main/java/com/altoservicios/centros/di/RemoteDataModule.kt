package com.altoservicios.centros.di

import com.altoservicios.centros.data.dataSource.remote.AuthRemoteDataSource
import com.altoservicios.centros.data.dataSource.remote.AuthRemoteDataSourceImpl
import com.altoservicios.centros.data.dataSource.remote.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    fun provideAuthRemoteDataSource(authService: AuthService): AuthRemoteDataSource = AuthRemoteDataSourceImpl(authService)

}