package com.fl.tracker.api

import com.fl.tracker.model.ScreenshotDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ScreenshotApi {

    companion object {
        const val API = "/api/screenshots"
    }

    @Multipart
    @POST(API)
    fun saveNewScreenshot(@Part("sessionId") sessionId: RequestBody, @Part file: MultipartBody.Part): Call<ScreenshotDto>
}