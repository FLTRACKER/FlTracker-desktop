package com.fl.tracker.model

import java.io.File

data class SaveNewScreenshotRequest(
    var sessionId: Long,
    var file: ByteArray,
)