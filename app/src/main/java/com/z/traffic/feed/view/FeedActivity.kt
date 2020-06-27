package com.z.traffic.feed.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.z.traffic.R
import com.z.traffic.feed.viewmodel.FeedViewModel
import com.z.traffic.feed.viewmodel.FeedViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.screen_feed.*
import javax.inject.Inject
import kotlin.math.abs


class FeedActivity : AppCompatActivity() {
    @Inject
    lateinit var picasso: Picasso
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val feedViewModel: FeedViewModel by viewModels { viewModelFactory }

    private lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        setContentView(R.layout.activity_feed)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, FeedMapFragment())
            .commit()

        initRecyclerView()

        initViewModelObservers()

        retryButton.setOnClickListener { refresh() }

        feedViewModel.refresh()
    }

    private fun initRecyclerView() {
        feedAdapter = FeedAdapter(picasso)
        recyclerView.adapter = feedAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                        val index = it.findFirstVisibleItemPosition()
                        feedViewModel.selectMapIndex(index)
                    }
                }
            }
        })

        LinearSnapHelper().attachToRecyclerView(recyclerView)
    }

    private fun initViewModelObservers() {
        feedViewModel.showLoading.observe(this, Observer(this::showLoading))
        feedViewModel.showInitialLoading.observe(this, Observer(this::showInitialLoading))
        feedViewModel.errorMessage.observe(this, Observer(this::onLoadError))
        feedViewModel.cameraData.observe(this, Observer(feedAdapter::setCameras))
        feedViewModel.listIndex.observe(this, Observer(this::selectAtIndex))
    }

    private fun onLoadError(errorMessage: String) {
        Snackbar.make(
            contentView,
            this.resources.getString(R.string.error_message),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(this.resources.getString(R.string.error_page_button)) {
            refresh()
        }.show()
    }

    private fun showInitialLoading(show: Boolean) {
        screenLoading.isGone = !show
    }

    private fun showLoading(show: Boolean) {
        if (!show) {
            retryButton.revertAnimation {
                retryButton.text = this.getText(R.string.refresh)
            }
        }
    }

    private fun selectAtIndex(index: Int) {
        var currentIndex = 0
        (recyclerView.layoutManager as? LinearLayoutManager)?.let {
            currentIndex = it.findFirstVisibleItemPosition()
        }
        if (abs(currentIndex - index) > 5) {
            recyclerView.scrollToPosition(index)
        } else {
            val scroller = LinearSmoothScroller(recyclerView.context)
            scroller.targetPosition = index
            recyclerView.layoutManager?.startSmoothScroll(scroller)
        }
    }

    private fun refresh() {
        retryButton.startAnimation {
            feedViewModel.refresh()
        }
    }
}