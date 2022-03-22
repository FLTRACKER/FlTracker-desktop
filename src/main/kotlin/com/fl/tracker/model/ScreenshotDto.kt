package com.fl.tracker.model

import java.time.LocalDateTime

data class ScreenshotDto(
    var path: String,
    var createdTime: LocalDateTime,
)