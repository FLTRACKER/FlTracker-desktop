package com.fl.tracker.api

import com.fl.tracker.model.ActiveWindowDto
import com.fl.tracker.model.ActivityDto
import com.fl.tracker.model.AddNewActivityRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityApi {

    companion object {
        const val API = "/api/activities"
    }

    @POST(API)
    fun addNewActivityInSession(@Body request: AddNewActivityRequest): Call<ActivityDto>

    @POST("$API/{id}/addActiveWindow")
    fun saveActiveWindowInfo(@Path("id") id: Long, @Body activeWindow: ActiveWindowDto): Call<ActiveWindowDto>
}