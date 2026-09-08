package com.altoservicios.centros.di


import com.altoservicios.centros.data.dataSource.remote.AuthRemoteDataSource
import com.altoservicios.centros.data.repository.AuthRepositoryImpl
import com.altoservicios.centros.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(authRemoteDataSource: AuthRemoteDataSource): AuthRepository = AuthRepositoryImpl(authRemoteDataSource)

}