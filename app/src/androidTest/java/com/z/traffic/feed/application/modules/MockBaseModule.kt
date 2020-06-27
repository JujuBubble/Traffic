package com.z.traffic.feed.application.modules

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class MockBaseModule {
    @Singleton
    @Provides
    fun converterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun retrofit(httpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()

    @Singleton
    @Provides
    fun httpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun providesPicasso(application: Application): Picasso = Picasso.Builder(application)
        .downloader(OkHttp3Downloader(OkHttpClient.Builder().build()))
        .build()

    companion object {
        private const val BASE_URL = "https://api.data.gov.sg/v1/transport/"
    }
}