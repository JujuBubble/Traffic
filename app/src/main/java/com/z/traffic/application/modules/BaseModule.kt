package com.z.traffic.application.modules

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.z.traffic.application.scopes.BaseScope
import com.z.traffic.feed.api.CamerasApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
class BaseModule {
    @BaseScope
    @Provides
    fun converterFactory(): Converter.Factory = GsonConverterFactory.create()

    @BaseScope
    @Provides
    fun httpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val builder: Request.Builder =
                original.newBuilder().method(original.method, original.body)
            builder.header("Content-Type", "application/json")
            chain.proceed(builder.build())
        }
        .build()

    @BaseScope
    @Provides
    fun retrofit(httpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()

    @BaseScope
    @Provides
    fun providesPicasso(application: Application): Picasso {
        val cache = getCacheFor(application)

        val client = OkHttpClient.Builder()
            .cache(cache)
            .build()

        return Picasso.Builder(application)
            .downloader(OkHttp3Downloader(client))
            .build()
    }

    private fun getCacheFor(application: Application): Cache {
        return Cache(File(application.cacheDir, PICASSO_CACHE), PICASSO_DISK_CACHE_SIZE)
    }

    companion object {
        private const val PICASSO_DISK_CACHE_SIZE = (1024 * 1024 * 30).toLong()
        private const val PICASSO_CACHE = "picassocache"
        private const val BASE_URL = "https://api.data.gov.sg/v1/transport/"
    }
}