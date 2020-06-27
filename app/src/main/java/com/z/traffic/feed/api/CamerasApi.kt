package com.z.traffic.feed.api

import com.z.traffic.feed.models.CamerasServiceResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject


class CamerasApi @Inject constructor(retrofit: Retrofit) {

    private val service = retrofit.create(CamerasService::class.java)

    fun getCameras(dateTime: String): Call<CamerasServiceResponse> {
        return service.getCameras(dateTime)
    }

    interface CamerasService {
        @GET("traffic-images")
        fun getCameras(@Query("date_time") dateTime: String): Call<CamerasServiceResponse>
    }
}