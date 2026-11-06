package com.altoservicios.centros.di

import com.altoservicios.centros.core.Config
import com.altoservicios.centros.data.dataSource.remote.service.AuthService
import com.altoservicios.centros.data.dataSource.remote.service.ContactService
import com.altoservicios.centros.data.dataSource.remote.service.PhotoService
import com.altoservicios.centros.data.dataSource.remote.service.ResidenceService
import com.altoservicios.centros.data.dataSource.remote.service.ResidencesService
import com.altoservicios.centros.data.dataSource.remote.service.UserService
import com.altoservicios.centros.data.datastore.AuthDataStore
import com.altoservicios.centros.data.network.AuthInterceptor
import com.altoservicios.centros.data.network.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authDataStore: AuthDataStore): OkHttpClient {
        // Cliente aparte, sin authenticator, para que refresh no se reintente a si mismo
        val refreshService = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor(authDataStore)).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authDataStore))
            .authenticator(TokenAuthenticator(authDataStore, refreshService))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Config.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideResidenceService(retrofit: Retrofit): ResidenceService {
        return retrofit.create(ResidenceService::class.java)
    }

    @Provides
    @Singleton
    fun provideResidencesService(retrofit: Retrofit): ResidencesService {
        return retrofit.create(ResidencesService::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoService(retrofit: Retrofit): PhotoService {
        return retrofit.create(PhotoService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideContactService(retrofit: Retrofit): ContactService {
        return retrofit.create(ContactService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}