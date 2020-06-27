package com.z.traffic.application.components

import android.app.Application
import com.z.traffic.MainApplication
import com.z.traffic.application.modules.*
import com.z.traffic.application.scopes.BaseScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@BaseScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        BaseModule::class,
        ApiModule::class,
        DataSourceModule::class,
        ViewModelFactoryModule::class
    ]
)
interface BaseComponent : AndroidInjector<MainApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): BaseComponent
    }
}
