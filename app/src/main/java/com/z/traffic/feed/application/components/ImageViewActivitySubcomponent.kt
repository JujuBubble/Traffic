package com.z.traffic.feed.application.components

import com.z.traffic.feed.application.scopes.ImageViewActivityScope
import com.z.traffic.feed.view.ImageViewActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ImageViewActivityScope
@Subcomponent
interface ImageViewActivitySubcomponent : AndroidInjector<ImageViewActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ImageViewActivity>()
}


