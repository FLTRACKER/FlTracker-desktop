package com.fl.tracker.repo

import com.fl.tracker.api.SessionApi
import com.fl.tracker.model.SessionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionRepo(
    private val api: SessionApi
) {
    suspend fun createNewSession(sessionDto: SessionDto): Result<Long> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                Result.success(api.createNewSession(sessionDto).execute().body()!!)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun findAll(): Result<List<SessionDto>> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                Result.success(api.findAll().execute().body()!!)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}