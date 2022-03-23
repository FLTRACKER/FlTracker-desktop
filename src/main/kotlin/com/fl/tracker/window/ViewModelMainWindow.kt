package com.fl.tracker.window

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.fl.tracker.service.ScreenRecorder

class ViewModelMainWindow {

	private val _isActive = mutableStateOf(false)
	val isActive: State<Boolean> = _isActive

	private val _isPause = mutableStateOf(false)
	val isPause: State<Boolean> = _isPause

	val screenRecorder = ScreenRecorder()

	fun getActiveWindows() =
		screenRecorder.activeWindows

	fun startSession() {
		screenRecorder.start()
		_isActive.value = true
		_isPause.value = false
		screenRecorder.countScreen.value = 0
		screenRecorder.activeWindows.clear()
	}

	fun stopSession() {
		screenRecorder.stop()
		_isActive.value = false
		_isPause.value = false
	}

	fun pauseSession() {
		val isPause = _isPause.value
		if (!isPause) {
			screenRecorder.pause()
		} else {
			screenRecorder.start()
		}
		_isPause.value = !isPause
	}
}