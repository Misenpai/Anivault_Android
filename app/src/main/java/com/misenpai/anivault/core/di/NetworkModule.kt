package com.misenpai.anivault.core.di

import android.app.Application
import com.misenpai.anivault.R
import com.misenpai.anivault.data.remote.api.AniVaultApi
import com.misenpai.anivault.data.remote.api.JikanApi
import com.misenpai.anivault.data.remote.interceptors.AuthInterceptor
import com.misenpai.anivault.data.remote.interceptors.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(app: Application): NetworkConnectionInterceptor = NetworkConnectionInterceptor(app)

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()


    @Provides
    @Singleton
    @Named("jikan")
    fun provideJikanOkHttpClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideJikanApi(app: Application,@Named("jikan") okHttpClient: OkHttpClient): JikanApi {
        return Retrofit.Builder().baseUrl(app.getString(R.string.JIKAN_API)).client(okHttpClient).addConverterFactory(
            GsonConverterFactory.create()).build().create(JikanApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAniVaultApi(app: Application, @Named("anivault")okHttpClient: OkHttpClient): AniVaultApi {
        return Retrofit.Builder().baseUrl(app.getString(R.string.AWS_API)).client(okHttpClient).addConverterFactory(
            GsonConverterFactory.create()).build().create(AniVaultApi::class.java)
    }
}