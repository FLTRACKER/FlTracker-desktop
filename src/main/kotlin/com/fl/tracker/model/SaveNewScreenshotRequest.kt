package com.fl.tracker.model

data class SaveNewScreenshotRequest(
	var sessionId: Long,
	var file: ByteArray,
) {

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as SaveNewScreenshotRequest

		if (sessionId != other.sessionId) return false
		if (!file.contentEquals(other.file)) return false

		return true
	}

	override fun hashCode(): Int {
		var result = sessionId.hashCode()
		result = 31 * result + file.contentHashCode()
		return result
	}
}