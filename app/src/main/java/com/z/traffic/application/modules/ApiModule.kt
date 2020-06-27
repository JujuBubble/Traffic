package com.z.traffic.application.modules

import com.z.traffic.application.scopes.BaseScope
import com.z.traffic.feed.api.CamerasApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {
    @BaseScope
    @Provides
    fun providesApi(retrofit: Retrofit): CamerasApi = CamerasApi(retrofit)
}