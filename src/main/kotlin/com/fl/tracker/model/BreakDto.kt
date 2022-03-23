package com.fl.tracker.model

import java.time.LocalDateTime

data class BreakDto(
	var startTime: LocalDateTime,
	var finishTime: LocalDateTime,
	var description: String,
)