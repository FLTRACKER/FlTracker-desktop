package com.fl.tracker.api

import com.fl.tracker.model.AddNewBreakRequest
import com.fl.tracker.model.BreakDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BreakApi {

	companion object {

		const val API = "/api/breaks"
	}

	@POST(API)
	fun addNewBreakInSession(@Body request: AddNewBreakRequest): Call<BreakDto>
}