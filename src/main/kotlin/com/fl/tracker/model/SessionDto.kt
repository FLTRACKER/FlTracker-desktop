package com.fl.tracker.model

data class SessionDto(
    var id: Long? = null,
    var startTime: String? = null,
    var finishTime: String? = null,
    var user: UserDto? = null,
    var breaks: List<BreakDto>? = null,
    var screenshots: List<ScreenshotDto>? = null,
    var activities: List<ActivityDto>? = null,
)