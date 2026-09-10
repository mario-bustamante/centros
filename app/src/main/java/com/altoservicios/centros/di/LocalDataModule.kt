package com.altoservicios.centros.di

import com.altoservicios.centros.data.dataSource.local.AuthLocalDataSource
import com.altoservicios.centros.data.dataSource.local.AuthLocalDataSourceImp
import com.altoservicios.centros.data.datastore.AuthDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    fun provideAuthLocalDataSource(authDataStore: AuthDataStore): AuthLocalDataSource = AuthLocalDataSourceImp(authDataStore)

}