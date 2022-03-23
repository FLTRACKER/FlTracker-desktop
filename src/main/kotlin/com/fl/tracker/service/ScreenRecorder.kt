package com.fl.tracker.service

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.fl.tracker.core.RemoteNetwork
import com.fl.tracker.model.ActiveWindow
import com.fl.tracker.model.SaveNewScreenshotRequest
import com.fl.tracker.model.SessionDto
import com.fl.tracker.model.UserDto
import com.fl.tracker.repo.ScreenshotRepo
import com.fl.tracker.repo.SessionRepo
import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.Psapi
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinNT.PROCESS_QUERY_INFORMATION
import com.sun.jna.platform.win32.WinNT.PROCESS_VM_READ
import com.sun.jna.ptr.IntByReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.imageio.ImageIO
import kotlin.concurrent.timerTask

class ScreenRecorder {

	companion object {

		private const val MAX_TITLE_LENGTH = 1024
		private val FOLDER_IMG = File("IMG")
	}

	private val timeOutScreenShoot = 60 // sec
	private var timer: Timer? = null
	var countScreen = mutableStateOf(0)
	var activeWindows = mutableStateListOf<ActiveWindow>()
	var idSession = mutableStateOf(0L)
	private var session: SessionDto? = null
	private val sessionRepo = SessionRepo(RemoteNetwork.sessionApi)
	private val screenRepo = ScreenshotRepo(RemoteNetwork.screenshotApi)

	private fun createSession() {
		if (session == null) {
			session = SessionDto(
				user = UserDto(1, "хуй"),
				startTime = LocalDateTime.now().format((DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"))))
			)
			CoroutineScope(Dispatchers.IO).launch {
				idSession.value = sessionRepo.createNewSession(session!!).getOrThrow()
			}
		}
	}

	private fun postImg(bufferedImage: BufferedImage) {
		val outStream = ByteArrayOutputStream()
		ImageIO.write(bufferedImage, "jpg", outStream)
		val screen = SaveNewScreenshotRequest(idSession.value, outStream.toByteArray())

		CoroutineScope(Dispatchers.IO).launch {
			screenRepo.saveNewScreenshot(screen)
		}
	}

	fun start() {
		createSession()
		pause()
		timer = Timer()
		timer?.schedule(timerTask {
			countScreen.value++
			if (countScreen.value % timeOutScreenShoot == 0) {
				FOLDER_IMG.mkdir()
				val date = LocalDateTime.now()
				val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy_HH.mm")
				val imageName = date.format(dateFormatter)
				val buffer = Robot().createScreenCapture(Rectangle(Toolkit.getDefaultToolkit().screenSize))
				postImg(buffer)
				val file = File("$FOLDER_IMG/$imageName.jpg")
				ImageIO.write(buffer, "jpg", file)
				println("Старт")
			}
			checkProcess()
			start()
		}, 1000)
	}

	private fun checkProcess() {
		if (Platform.isWindows()) {
			try {
				val buffer = CharArray(MAX_TITLE_LENGTH * 2)
				val userWin = User32.INSTANCE.GetForegroundWindow()
				User32.INSTANCE.GetWindowText(userWin, buffer, MAX_TITLE_LENGTH)
				val titleStr = Native.toString(buffer)
				val pid = IntByReference()
				User32.INSTANCE.GetWindowThreadProcessId(userWin, pid)
				val process =
					Kernel32.INSTANCE.OpenProcess(PROCESS_QUERY_INFORMATION or PROCESS_VM_READ, false, pid.value)
				Psapi.INSTANCE.GetModuleFileNameExW(process, null, buffer, MAX_TITLE_LENGTH)
				var processStr = Native.toString(buffer)
				val indexLastSlash = processStr.lastIndexOf('\\') + 1
				processStr = processStr.substring(indexLastSlash)
				var nameTitle = "$titleStr ($processStr)"

				if (titleStr.isEmpty()) {
					nameTitle = "($processStr)"
				}
//                println("Active window title: $nameTitle")
				val activeWindow = ActiveWindow(titleStr, processStr, 0)
				val wind = activeWindows.find { it.process == processStr }
				if (wind != null) {
					activeWindows.remove(wind)
					activeWindow.time = wind.time + 1
				}
				activeWindows.add(activeWindow)

			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	fun pause() {
		timer?.cancel()
		timer = null
	}

	fun stop() {
		pause()
		countScreen.value = 0
		session = null
	}
}