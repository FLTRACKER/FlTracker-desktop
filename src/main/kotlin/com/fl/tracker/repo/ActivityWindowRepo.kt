package com.fl.tracker.repo

import com.fl.tracker.api.ActivityApi
import com.fl.tracker.model.ActiveWindowDto
import com.fl.tracker.model.ActivityDto
import com.fl.tracker.model.AddNewActivityRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivityWindowRepo(
	private val api: ActivityApi
) {

	suspend fun addNewActivityInSession(request: AddNewActivityRequest): Result<ActivityDto> {
		return withContext(Dispatchers.IO) {
			return@withContext try {
				Result.success(api.addNewActivityInSession(request).execute().body()!!)
			} catch (e: Exception) {
				Result.failure(e)
			}
		}
	}

	suspend fun saveActiveWindowInfo(id: Long, activeWindow: ActiveWindowDto): Result<ActiveWindowDto> {
		return withContext(Dispatchers.IO) {
			return@withContext try {
				Result.success(api.saveActiveWindowInfo(id, activeWindow).execute().body()!!)
			} catch (e: Exception) {
				Result.failure(e)
			}
		}
	}
}