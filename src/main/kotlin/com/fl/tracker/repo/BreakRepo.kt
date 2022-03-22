package com.fl.tracker.repo

import com.fl.tracker.api.BreakApi
import com.fl.tracker.model.AddNewBreakRequest
import com.fl.tracker.model.BreakDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BreakRepo(
    private val api: BreakApi
) {
    suspend fun addNewBreakInSession(request: AddNewBreakRequest): Result<BreakDto> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                Result.success(api.addNewBreakInSession(request).execute().body()!!)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}