package com.fl.tracker.model

import java.time.LocalTime

data class ActiveWindowDto(
    var title: String,
    var totalTime: LocalTime,
)