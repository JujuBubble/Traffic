package com.z.traffic.application.modules

import androidx.lifecycle.ViewModelProvider
import com.z.traffic.application.scopes.BaseScope
import com.z.traffic.feed.datasource.DataSource
import com.z.traffic.feed.viewmodel.FeedViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {
    @BaseScope
    @Provides
    fun providesViewModelFactory(dataSource: DataSource): ViewModelProvider.Factory =
        FeedViewModelFactory(dataSource)
}