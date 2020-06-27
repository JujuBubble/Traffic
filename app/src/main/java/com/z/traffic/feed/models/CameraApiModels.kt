package com.z.traffic.feed.models

import com.google.gson.annotations.SerializedName

data class CamerasServiceResponse(
    @SerializedName("api_info")
    val info: ApiInfo? = null,
    @SerializedName("items")
    val content: List<Content>? = null
)

data class ApiInfo(
    @SerializedName("status")
    val status: String? = null
)

data class Content(
    @SerializedName("timestamp")
    val timestamp: String? = null,
    @SerializedName("cameras")
    val cameras: List<Camera>? = null
)

data class Camera(
    @SerializedName("timestamp")
    val timestamp: String = "",
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("camera_id")
    val cameraId: String,
    @SerializedName("image_metadata")
    val imageMetadata: ImageData
)

data class Location(
    @SerializedName("latitude")
    val latitude: Float = 0.toFloat(),
    @SerializedName("longitude")
    val longitude: Float = 0.toFloat()
)

data class ImageData(
    @SerializedName("height")
    val height: Int = 0,
    @SerializedName("width")
    val width: Int = 0,
    @SerializedName("md5")
    val md5: String? = null
)