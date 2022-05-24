package com.aminejbr.doublesymmetrytask.di

import com.aminejbr.doublesymmetrytask.common.Constants
import com.aminejbr.doublesymmetrytask.data.MusicApi
import com.aminejbr.doublesymmetrytask.data.repository.MusicsRepositoryImpl
import com.aminejbr.doublesymmetrytask.domain.repository.MusicsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = (HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesMusicApi(okHttpClient: OkHttpClient): MusicApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MusicApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMusicsRepository(api: MusicApi): MusicsRepository {
        return MusicsRepositoryImpl(api)
    }
}