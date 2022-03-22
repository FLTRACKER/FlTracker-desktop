package com.fl.tracker.api

import com.fl.tracker.model.SessionDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SessionApi {

    companion object {
        const val API = "/api/sessions"
    }

    @POST(API)
    fun createNewSession(@Body sessionDto: SessionDto): Call<Long>

    @GET(API)
    fun findAll(): Call<List<SessionDto>>
}