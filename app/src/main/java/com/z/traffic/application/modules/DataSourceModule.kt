package com.z.traffic.application.modules

import androidx.lifecycle.ViewModelProvider
import com.z.traffic.application.scopes.BaseScope
import com.z.traffic.feed.api.CamerasApi
import com.z.traffic.feed.datasource.CamerasDataSource
import com.z.traffic.feed.datasource.DataSource
import com.z.traffic.feed.viewmodel.FeedViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {
    @BaseScope
    @Provides
    fun providesDataSource(api: CamerasApi): DataSource = CamerasDataSource(api)
}