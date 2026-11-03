package com.altoservicios.centros.di


import com.altoservicios.centros.domain.repository.AuthRepository
import com.altoservicios.centros.domain.repository.ContactRepository
import com.altoservicios.centros.domain.repository.PhotoRepository
import com.altoservicios.centros.domain.repository.ResidencesRepository
import com.altoservicios.centros.domain.repository.UserRepository
import com.altoservicios.centros.domain.useCase.auth.AuthUseCase
import com.altoservicios.centros.domain.useCase.auth.GetSessionDataUseCase
import com.altoservicios.centros.domain.useCase.auth.LoginUseCase
import com.altoservicios.centros.domain.useCase.auth.LogoutUseCase
import com.altoservicios.centros.domain.useCase.auth.RegisterUseCase
import com.altoservicios.centros.domain.useCase.auth.SaveSessionUseCase
import com.altoservicios.centros.domain.useCase.contact.ContactUseCase
import com.altoservicios.centros.domain.useCase.contact.GetContactValuesUseCase
import com.altoservicios.centros.domain.useCase.photo.DeletePhotoUseCase
import com.altoservicios.centros.domain.useCase.photo.GetPhotosUseCase
import com.altoservicios.centros.domain.useCase.photo.PhotoUseCase
import com.altoservicios.centros.domain.useCase.photo.UpdatePhotoUseCase
import com.altoservicios.centros.domain.useCase.photo.UploadPhotoUseCase
import com.altoservicios.centros.domain.useCase.residences.GetDependenciesUseCase
import com.altoservicios.centros.domain.useCase.residences.GetMyDependenciesUseCase
import com.altoservicios.centros.domain.useCase.residences.GetMyResidenceUseCase
import com.altoservicios.centros.domain.useCase.residences.GetMyRoomsUseCase
import com.altoservicios.centros.domain.useCase.residences.GetMySectorsUseCase
import com.altoservicios.centros.domain.useCase.residences.GetPricesUseCase
import com.altoservicios.centros.domain.useCase.residences.GetProvincesUseCase
import com.altoservicios.centros.domain.useCase.residences.GetResidencesMapUseCase
import com.altoservicios.centros.domain.useCase.residences.GetResidencesUseCase
import com.altoservicios.centros.domain.useCase.residences.GetRoomsUseCase
import com.altoservicios.centros.domain.useCase.residences.GetSectorsUseCase
import com.altoservicios.centros.domain.useCase.residences.GetTownsUseCase
import com.altoservicios.centros.domain.useCase.residences.ResidencesUseCase
import com.altoservicios.centros.domain.useCase.residences.UpdateMyResidenceUseCase
import com.altoservicios.centros.domain.useCase.user.GetUserUseCase
import com.altoservicios.centros.domain.useCase.user.UpdateUserUseCase
import com.altoservicios.centros.domain.useCase.user.UserUseCase
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
    fun provideContactUseCase(repository: ContactRepository) = ContactUseCase(
        getValues = GetContactValuesUseCase(repository)
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