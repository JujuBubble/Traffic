package com.z.traffic

import com.z.traffic.application.components.BaseComponent
import com.z.traffic.feed.application.components.DaggerMockBaseComponent
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

open class InstrumentationTestApplication : MainApplication(), HasAndroidInjector {

    lateinit var baseComponent: BaseComponent

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        baseComponent = DaggerMockBaseComponent.builder()
            .application(this)
            .build().also {
                it.inject(this)
            }
    }
}