package com.fl.tracker.core

import com.fl.tracker.api.ActivityApi
import com.fl.tracker.api.BreakApi
import com.fl.tracker.api.ScreenshotApi
import com.fl.tracker.api.SessionApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteNetwork {

	private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
	private val httpClient = provideHttpClient()
	private val retrofit = Retrofit.Builder()
		.client(httpClient)
		.baseUrl("http://localhost:8080")
		.addConverterFactory(GsonConverterFactory.create(gson))
		.build()

	private fun provideHttpClient(): OkHttpClient {
		val httpClient = OkHttpClient.Builder()
		httpClient.callTimeout(10, TimeUnit.SECONDS)
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY
		httpClient.addInterceptor(logging)

		return httpClient.build()
	}

	val activityApi: ActivityApi = retrofit.create(ActivityApi::class.java)
	val breakApi: BreakApi = retrofit.create(BreakApi::class.java)
	val screenshotApi: ScreenshotApi = retrofit.create(ScreenshotApi::class.java)
	val sessionApi: SessionApi = retrofit.create(SessionApi::class.java)
//    val userApi = retrofit.create(ActivityApi::class.java)
}