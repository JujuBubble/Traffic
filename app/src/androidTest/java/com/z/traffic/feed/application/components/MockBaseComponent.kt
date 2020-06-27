package com.z.traffic.feed.application.components

import android.app.Application
import com.z.traffic.application.components.BaseComponent
import com.z.traffic.application.modules.ActivityModule
import com.z.traffic.application.scopes.BaseScope
import com.z.traffic.feed.application.modules.MockBaseModule
import com.z.traffic.feed.application.modules.MockDataSourceModule
import com.z.traffic.feed.list.FeedActivityTest
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        MockBaseModule::class,
        MockDataSourceModule::class
    ]
)
interface MockBaseComponent : BaseComponent {
    fun inject(arg: FeedActivityTest)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MockBaseComponent
    }
}
