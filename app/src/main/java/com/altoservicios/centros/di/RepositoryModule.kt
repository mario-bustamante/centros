package com.altoservicios.centros.di


import com.altoservicios.centros.data.dataSource.local.AuthLocalDataSource
import com.altoservicios.centros.data.dataSource.remote.AuthRemoteDataSource
import com.altoservicios.centros.data.repository.AuthRepositoryImpl
import com.altoservicios.centros.data.dataSource.remote.service.PhotoService
import com.altoservicios.centros.data.dataSource.remote.service.ResidenceService
import com.altoservicios.centros.data.dataSource.remote.service.UserService
import com.altoservicios.centros.data.repository.PhotoRepositoryImpl
import com.altoservicios.centros.data.repository.ResidencesRepositoryImpl
import com.altoservicios.centros.data.repository.UserRepositoryImpl
import com.altoservicios.centros.domain.repository.PhotoRepository
import com.altoservicios.centros.domain.repository.ResidencesRepository
import com.altoservicios.centros.domain.repository.UserRepository
import com.altoservicios.centros.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        authLocalDataSource: AuthLocalDataSource
    ): AuthRepository = AuthRepositoryImpl(authRemoteDataSource, authLocalDataSource)

    @Provides
    fun provideResidencesRepository(service: ResidenceService): ResidencesRepository = ResidencesRepositoryImpl(service)

    @Provides
    fun providePhotoRepository(service: PhotoService): PhotoRepository = PhotoRepositoryImpl(service)

    @Provides
    fun provideUserRepository(service: UserService): UserRepository = UserRepositoryImpl(service)

}