package com.z.traffic.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.z.traffic.feed.datasource.DataSource

class FeedViewModelFactory constructor(private val dataSource: DataSource) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            FeedViewModelImpl(dataSource) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}
