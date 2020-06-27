package com.z.traffic.feed.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.z.traffic.feed.api.CamerasApi
import com.z.traffic.feed.models.Camera
import com.z.traffic.feed.models.CamerasServiceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


open class CamerasDataSource constructor(private val camerasApi: CamerasApi) : DataSource {

    private var trafficCamerasData: MutableLiveData<List<Camera>> = MutableLiveData()
    private var errorState: MutableLiveData<Error?> = MutableLiveData()
    private var loadingState: MutableLiveData<Boolean> = MutableLiveData()
    private var firstLoadingState: MutableLiveData<Boolean> = MutableLiveData(true)

    override fun fetchCameraData() {
        errorState.postValue(null)
        loadingState.postValue(true)

        val dateTime = currentDateTime()
        camerasApi.getCameras(dateTime)
            .enqueue(object : Callback<CamerasServiceResponse?> {
                override fun onResponse(
                    call: Call<CamerasServiceResponse?>?,
                    response: Response<CamerasServiceResponse?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.content?.first()
                            ?.cameras?.let(trafficCamerasData::setValue)
                        errorState.postValue(null)
                    }
                    loadingState.postValue(false)
                    firstLoadingState.postValue(false)
                }

                override fun onFailure(call: Call<CamerasServiceResponse?>?, t: Throwable?) {
                    errorState.postValue(Error(t?.localizedMessage))
                    loadingState.postValue(false)
                    firstLoadingState.postValue(false)
                }
            })
    }

    private fun currentDateTime(): String {
        val pattern = DATE_FORMAT
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return current.format(formatter)
    }

    override fun cameraData(): LiveData<List<Camera>> = trafficCamerasData
    override fun errorState(): LiveData<Error?> = errorState
    override fun loadingState(): LiveData<Boolean> = loadingState
    override fun firstLoadingState(): LiveData<Boolean> = firstLoadingState

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    }
}

interface DataSource {
    fun cameraData(): LiveData<List<Camera>>
    fun errorState(): LiveData<Error?>
    fun loadingState(): LiveData<Boolean>
    fun firstLoadingState(): LiveData<Boolean>
    fun fetchCameraData()
}
