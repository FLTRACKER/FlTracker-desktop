package com.fl.tracker.model

data class AddNewBreakRequest(
    var sessionId: Long,
    val breakPayload: BreakDto,
)
