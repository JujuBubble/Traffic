package com.z.traffic.application.modules

import com.z.traffic.feed.application.components.FeedActivitySubcomponent
import com.z.traffic.feed.application.components.FeedMapFragmentSubcomponent
import com.z.traffic.feed.application.components.ImageViewActivitySubcomponent
import com.z.traffic.feed.view.FeedActivity
import com.z.traffic.feed.view.FeedMapFragment
import com.z.traffic.feed.view.ImageViewActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [
        FeedActivitySubcomponent::class,
        FeedMapFragmentSubcomponent::class,
        ImageViewActivitySubcomponent::class
    ]
)
abstract class ActivityModule {
    @Binds
    @IntoMap
    @ClassKey(FeedActivity::class)
    abstract fun bindFeedActivityInjectorFactory(builder: FeedActivitySubcomponent.Builder): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(FeedMapFragment::class)
    abstract fun bindFeedMapFragmentInjectorFactory(builder: FeedMapFragmentSubcomponent.Builder): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(ImageViewActivity::class)
    abstract fun bindImageViewActivityInjectorFactory(builder: ImageViewActivitySubcomponent.Builder): AndroidInjector.Factory<*>
}
