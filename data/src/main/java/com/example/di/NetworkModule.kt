package com.example.di

import android.app.Application
import com.example.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.data.remote.api.AuthInterceptor
import com.example.data.repository.MovieRepositoryImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        application: Application
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(ChuckerInterceptor.Builder(application) // Tambahkan ini
                .build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): com.example.data.remote.api.ApiService {
        return Retrofit.Builder()
            .baseUrl(com.example.common.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(com.example.data.remote.api.ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: com.example.data.remote.api.ApiService,
        application: Application
    ): MovieRepository {
        return MovieRepositoryImpl(apiService, application)
    }
}