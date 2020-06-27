package com.z.traffic.feed.application.modules

import androidx.lifecycle.ViewModelProvider
import com.z.traffic.feed.datasource.DataSource
import com.z.traffic.feed.mocks.MockCamerasDataSource
import com.z.traffic.feed.viewmodel.FeedViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockDataSourceModule {
    @Singleton
    @Provides
    fun providesDataSource(): DataSource = MockCamerasDataSource()

    @Singleton
    @Provides
    fun providesViewModelFactory(dataSource: DataSource): ViewModelProvider.Factory =
        FeedViewModelFactory(dataSource)
}