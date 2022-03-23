package com.fl.tracker.model

data class ActiveWindow(
	var title: String,
	val process: String,
	var time: Long
) : Comparable<ActiveWindow> {

	override fun compareTo(other: ActiveWindow): Int {
		return when {
			process == other.process && time == other.time -> 0
			process != other.process && time >= other.time -> 1
			process != other.process && time < other.time -> -1
			else -> 1
		}
	}

	override fun equals(other: Any?): Boolean {
		val wind = other as ActiveWindow?
		if (process == wind?.process) {
			return true
		}
		return super.equals(other)
	}

	override fun hashCode(): Int {
		var result = title.hashCode()
		result = 31 * result + process.hashCode()
		result = 31 * result + time.hashCode()
		return result
	}
}