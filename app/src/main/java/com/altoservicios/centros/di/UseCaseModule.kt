package com.altoservicios.centros.di


import com.altoservicios.centros.data.dataSource.remote.AuthRemoteDataSource
import com.altoservicios.centros.data.repository.AuthRepositoryImpl
import com.altoservicios.centros.domain.repository.AuthRepository
import com.altoservicios.centros.domain.useCase.auth.AuthUseCase
import com.altoservicios.centros.domain.useCase.auth.GetSessionDataUseCase
import com.altoservicios.centros.domain.useCase.auth.LoginUseCase
import com.altoservicios.centros.domain.useCase.auth.LogoutUseCase
import com.altoservicios.centros.domain.useCase.auth.RegisterUseCase
import com.altoservicios.centros.domain.useCase.auth.SaveSessionUseCase
import com.altoservicios.centros.domain.repository.PhotoRepository
import com.altoservicios.centros.domain.repository.ResidencesRepository
import com.altoservicios.centros.domain.repository.UserRepository
import com.altoservicios.centros.domain.useCase.photo.*
import com.altoservicios.centros.domain.useCase.residences.*
import com.altoservicios.centros.domain.useCase.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository) = AuthUseCase(
        login = LoginUseCase(authRepository),
        register = RegisterUseCase(authRepository),
        saveSession = SaveSessionUseCase(authRepository),
        getSession = GetSessionDataUseCase(authRepository),
        logout = LogoutUseCase(authRepository)
    )

    @Provides
    fun provideResidencesUseCase(repository: ResidencesRepository) = ResidencesUseCase(
        getResidences = GetResidencesUseCase(repository),
        getResidencesMap = GetResidencesMapUseCase(repository),
        getProvinces = GetProvincesUseCase(repository),
        getTowns = GetTownsUseCase(repository),
        getRooms = GetRoomsUseCase(repository),
        getSectors = GetSectorsUseCase(repository),
        getDependencies = GetDependenciesUseCase(repository),
        getPrices = GetPricesUseCase(repository),
        getMyResidence = GetMyResidenceUseCase(repository),
        updateMyResidence = UpdateMyResidenceUseCase(repository),
        getMyRooms = GetMyRoomsUseCase(repository),
        getMySectors = GetMySectorsUseCase(repository),
        getMyDependencies = GetMyDependenciesUseCase(repository)
    )

    @Provides
    fun providePhotoUseCase(repository: PhotoRepository) = PhotoUseCase(
        getPhotos = GetPhotosUseCase(repository),
        uploadPhoto = UploadPhotoUseCase(repository),
        updatePhoto = UpdatePhotoUseCase(repository),
        deletePhoto = DeletePhotoUseCase(repository)
    )

    @Provides
    fun provideUserUseCase(repository: UserRepository) = UserUseCase(
        getUser = GetUserUseCase(repository),
        updateUser = UpdateUserUseCase(repository)
    )
}