package com.z.traffic.feed.application.components

import com.z.traffic.feed.application.scopes.FeedScope
import com.z.traffic.feed.view.FeedMapFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FeedScope
@Subcomponent
interface FeedMapFragmentSubcomponent : AndroidInjector<FeedMapFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FeedMapFragment>()
}

