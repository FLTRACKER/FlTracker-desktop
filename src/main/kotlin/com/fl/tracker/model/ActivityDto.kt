package com.fl.tracker.model

import java.time.LocalDateTime

data class ActivityDto(
	val startTime: LocalDateTime,
	val finishTime: LocalDateTime,
	val activeWindows: List<ActiveWindowDto>
)