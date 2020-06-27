package com.z.traffic.feed.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.ui.IconGenerator
import com.z.traffic.R
import com.z.traffic.feed.models.CameraMapMarkerInfo
import com.z.traffic.feed.viewmodel.FeedViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class FeedMapFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var feedViewModel: FeedViewModel

    private lateinit var map: GoogleMap
    private var mapReady = false

    private var lastClicked: Marker? = null
    private var locations: List<CameraMapMarkerInfo>? = null
    private var markers: HashMap<Int, Marker> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.feed_map_fragment, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            mapReady = true
            map.setOnMarkerClickListener {
                selectMarker(it)
            }
        }

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidSupportInjection.inject(this)

        activity?.let {
            feedViewModel =
                ViewModelProviders.of(it, viewModelFactory).get(FeedViewModel::class.java)
        }
        feedViewModel.cameraMapMarkerData.observe(this, Observer(this::updateMap))
        feedViewModel.mapIndex.observe(this, Observer(this::selectAtIndex))
    }

    private fun updateMap(newLocations: List<CameraMapMarkerInfo>) {
        if (mapReady && newLocations.isNotEmpty()) {
            markers.clear()
            lastClicked = null
            locations = newLocations
            map.clear()
            newLocations.forEachIndexed { index, mapMarker ->
                val marker = map.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            mapMarker.location.latitude.toDouble(),
                            mapMarker.location.longitude.toDouble()
                        )
                    ).icon(markerBitmap(mapMarker.title))
                )
                marker.tag = index
                markers[index] = marker
            }
            moveCamera(newLocations)
        }
    }

    private fun moveCamera(mapMarkers: List<CameraMapMarkerInfo>) {
        val builder = LatLngBounds.Builder()
        for (m in mapMarkers) {
            builder.include(LatLng(m.location.latitude.toDouble(), m.location.longitude.toDouble()))
        }
        val bounds: LatLngBounds = builder.build()
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, MAP_PADDING))
    }

    private fun markerBitmap(
        title: String?,
        selected: Boolean = false,
        appearance: Int = R.style.Map_Marker_Unselected
    ): BitmapDescriptor {
        val icg = IconGenerator(context)
        val color = if (selected) resources.getColor(R.color.text_dark) else Color.WHITE
        icg.setColor(color)
        icg.setTextAppearance(appearance)
        return BitmapDescriptorFactory.fromBitmap(icg.makeIcon(title))
    }

    private fun selectAtIndex(index: Int) {
        markers[index]?.let {
            selectMarker(it)
            val cameraUpdate = CameraUpdateFactory.newLatLng(it.position)
            map.animateCamera(cameraUpdate)
        }
    }

    private fun selectMarker(marker: Marker): Boolean {
        if (lastClicked != null) {
            val title = locations?.get(lastClicked?.tag as Int)?.title
            val newBitmap = markerBitmap(title, false, R.style.Map_Marker_Seen)
            lastClicked?.setIcon(newBitmap)
            lastClicked?.zIndex = 998F
        }

        val index = marker.tag as Int
        val title = locations?.get(index)?.title
        val newBitmap = markerBitmap(title, true, R.style.Map_Marker_Selected)
        marker.setIcon(newBitmap)
        lastClicked = marker
        marker.zIndex = 999F

        feedViewModel.selectIndex(index)

        return true
    }

    companion object {
        private const val MAP_PADDING = 50
    }
}