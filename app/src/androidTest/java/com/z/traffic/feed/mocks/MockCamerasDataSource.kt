package com.z.traffic.feed.mocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.z.traffic.feed.datasource.DataSource
import com.z.traffic.feed.models.Camera

class MockCamerasDataSource : DataSource {
    override fun cameraData(): LiveData<List<Camera>> = mockCameraData

    override fun errorState(): LiveData<Error?> = mockErrorState

    override fun loadingState(): LiveData<Boolean> = mockLoadingState

    override fun firstLoadingState(): LiveData<Boolean> = mockFirstLoadingState

    override fun fetchCameraData() {}

    private var mockCameraData = MutableLiveData<List<Camera>>()
    private var mockErrorState = MutableLiveData<Error?>()
    private var mockLoadingState = MutableLiveData<Boolean>()
    private var mockFirstLoadingState = MutableLiveData<Boolean>()

    fun setCameraData(list: List<Camera>) {
        mockCameraData.postValue(list)
    }

    fun setErrorState(value: Error?) {
        mockErrorState.postValue(value)
    }

    fun setLoadingState(value: Boolean) {
        mockLoadingState.postValue(value)
    }

    fun setFirstLoadingState(value: Boolean) {
        mockFirstLoadingState.postValue(value)
    }
}