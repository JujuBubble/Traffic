package com.z.traffic.feed.application.components

import com.z.traffic.feed.application.scopes.FeedScope
import com.z.traffic.feed.view.FeedActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FeedScope
@Subcomponent
interface FeedActivitySubcomponent : AndroidInjector<FeedActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FeedActivity>()
}

