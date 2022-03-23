package com.fl.tracker.model

data class SaveActiveWindowInfoRequest(
	var activityId: Long,
	val activeWindowPayload: ActiveWindowDto,
)