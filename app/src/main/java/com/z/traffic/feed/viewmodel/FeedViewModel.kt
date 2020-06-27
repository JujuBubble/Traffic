package com.z.traffic.feed.viewmodel

import androidx.lifecycle.*
import com.z.traffic.feed.datasource.DataSource
import com.z.traffic.feed.models.Camera
import com.z.traffic.feed.models.CameraMapMarkerInfo
import kotlinx.coroutines.launch


class FeedViewModelImpl constructor(private val dataSource: DataSource) : FeedViewModel() {

    override val listIndex: MutableLiveData<Int> = MutableLiveData()
    override val mapIndex: MutableLiveData<Int> = MutableLiveData()
    override val showLoading: LiveData<Boolean> = dataSource.loadingState()
    override val showInitialLoading: LiveData<Boolean> = dataSource.firstLoadingState()

    override val errorMessage: LiveData<String> = liveData {
        emitSource(dataSource.errorState().switchMap { error ->
            liveData {
                error?.localizedMessage?.let { emit(it) }
            }
        })
    }

    override val cameraData: LiveData<List<Camera>> = liveData { emitSource(dataSource.cameraData()) }

    override val cameraMapMarkerData: LiveData<List<CameraMapMarkerInfo>> =
        dataSource.cameraData().switchMap {
            liveData {
                emit(it.mapIndexed { position, camera ->
                    CameraMapMarkerInfo(
                        camera.location,
                        camera.cameraId,
                        position
                    )
                })
            }
        }

    override fun refresh() {
        viewModelScope.launch {
            dataSource.fetchCameraData()
        }
    }

    override fun selectIndex(index: Int) {
        listIndex.postValue(index)
    }

    override fun selectMapIndex(index: Int) {
        mapIndex.postValue(index)
    }
}

abstract class FeedViewModel: ViewModel() {
    abstract val showLoading: LiveData<Boolean>
    abstract val showInitialLoading: LiveData<Boolean>
    abstract val errorMessage: LiveData<String>
    abstract val cameraData: LiveData<List<Camera>>
    abstract val cameraMapMarkerData: LiveData<List<CameraMapMarkerInfo>>
    abstract val listIndex: MutableLiveData<Int>
    abstract val mapIndex: MutableLiveData<Int>

    abstract fun refresh()
    abstract fun selectIndex(index: Int)
    abstract fun selectMapIndex(index: Int)
}