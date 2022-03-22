package com.fl.tracker.repo

import com.fl.tracker.api.ScreenshotApi
import com.fl.tracker.model.SaveNewScreenshotRequest
import com.fl.tracker.model.ScreenshotDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class ScreenshotRepo(
    private val api: ScreenshotApi
) {
    suspend fun saveNewScreenshot(request: SaveNewScreenshotRequest): Result<ScreenshotDto> {
        return withContext(Dispatchers.IO) {
            return@withContext try {

                val requestFile: RequestBody = request.file.toRequestBody("multipart/form-data".toMediaType())

                val body: MultipartBody.Part = MultipartBody.Part.createFormData("file", "test", requestFile)

                val id: RequestBody = request.sessionId.toString().toRequestBody("application/json".toMediaType())

                Result.success(api.saveNewScreenshot(id, body).execute().body()!!)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
}