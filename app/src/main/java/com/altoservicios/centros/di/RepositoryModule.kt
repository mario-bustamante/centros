package com.altoservicios.centros.di


import com.altoservicios.centros.data.dataSource.local.AuthLocalDataSource
import com.altoservicios.centros.data.dataSource.remote.AuthRemoteDataSource
import com.altoservicios.centros.data.dataSource.remote.service.ContactService
import com.altoservicios.centros.data.dataSource.remote.service.PhotoService
import com.altoservicios.centros.data.dataSource.remote.service.ResidenceService
import com.altoservicios.centros.data.dataSource.remote.service.ResidencesService
import com.altoservicios.centros.data.dataSource.remote.service.UserService
import com.altoservicios.centros.data.repository.AuthRepositoryImpl
import com.altoservicios.centros.data.repository.ContactRepositoryImpl
import com.altoservicios.centros.data.repository.PhotoRepositoryImpl
import com.altoservicios.centros.data.repository.ResidencesRepositoryImpl
import com.altoservicios.centros.data.repository.UserRepositoryImpl
import com.altoservicios.centros.domain.repository.AuthRepository
import com.altoservicios.centros.domain.repository.ContactRepository
import com.altoservicios.centros.domain.repository.PhotoRepository
import com.altoservicios.centros.domain.repository.ResidencesRepository
import com.altoservicios.centros.domain.repository.UserRepository
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
    fun provideResidencesRepository(
        residenceService: ResidenceService,
        residencesService: ResidencesService
    ): ResidencesRepository = ResidencesRepositoryImpl(residenceService, residencesService)

    @Provides
    fun provideContactRepository(service: ContactService): ContactRepository = ContactRepositoryImpl(service)

    @Provides
    fun providePhotoRepository(service: PhotoService): PhotoRepository = PhotoRepositoryImpl(service)

    @Provides
    fun provideUserRepository(service: UserService): UserRepository = UserRepositoryImpl(service)

}