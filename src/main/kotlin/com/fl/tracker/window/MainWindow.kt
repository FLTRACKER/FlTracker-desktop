package com.fl.tracker.window

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.fl.tracker.AppColors
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainWindow {

	private val viewModel = ViewModelMainWindow()

	@Composable
	@Preview
	fun init(app: ApplicationScope) {
		Window(
			onCloseRequest = { app.exitApplication() },
			title = "FLTracker",
			state = rememberWindowState(width = 720.dp, height = 480.dp),
		) { mainColumn() }
	}

	@[Composable Preview]
	fun mainColumn() {
		val modifier = Modifier.background(AppColors.LIGHT)
		MaterialTheme {
			Column(modifier) {
				navRow()
				timeInfoRow()
				activeWindowsRow()
			}
		}
	}

	@[Composable Preview]
	fun navRow() {
		val modifier = Modifier
			.fillMaxWidth()
			.height(80.dp)
			.background(AppColors.GREY)

		Row(
			modifier = modifier,
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {
			val isActive = viewModel.isActive.value
			val isPause = viewModel.isPause.value

			navDateButton("Начать", !isActive && !isPause) { viewModel.startSession() }
			val nameButton = if (isPause) "Продолжить" else "Перерыв"
			navDateButton(nameButton, isActive) { viewModel.pauseSession() }

			navDateButton("Закончить", isActive) { viewModel.stopSession() }
		}
	}

	@[Composable Preview]
	fun navDateButton(btnName: String, enabled: Boolean, onClick: () -> Unit) {
		val modifier = Modifier
			.fillMaxHeight()
			.wrapContentWidth()
		val colors = ButtonDefaults.buttonColors(
			backgroundColor = AppColors.TEAL, contentColor = AppColors.GREY,
			disabledBackgroundColor = AppColors.DARK, disabledContentColor = AppColors.GREY,
		)

		Button(
			modifier = modifier, enabled = enabled, onClick = onClick,
			shape = RectangleShape, colors = colors
		) {
			Text(btnName)
		}
	}

	@[Composable Preview]
	fun timeInfoRow() {
		val modifier = Modifier
			.fillMaxWidth()
			.height(50.dp)
			.background(AppColors.LIGHT)

		Row(
			modifier = modifier,
			verticalAlignment = Alignment.CenterVertically
		) {
			Box(
				modifier = Modifier.weight(2f),
				contentAlignment = Alignment.Center,
			) {
				Text("Итого: ${calcTimes(viewModel.screenRecorder.countScreen.value)}")
			}

			Spacer(Modifier.weight(1f))

			Box(
				modifier = Modifier.weight(2f),
				contentAlignment = Alignment.Center,
			) {
				val date = LocalDateTime.now()
				val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")
				val dateStr = date.format(dateFormat)
				Text(dateStr)
			}
		}
	}

	@[Composable Preview]
	fun activeWindowsRow() {
		val modifierRow = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)

		for ((title, process, time) in viewModel.getActiveWindows()) {
			Spacer(modifier = Modifier.fillMaxWidth().height(2.dp).background(AppColors.DARK))
			Row(modifier = modifierRow, verticalAlignment = Alignment.CenterVertically) {
				Box(
					modifier = Modifier.weight(4f).padding(start = 10.dp),
					contentAlignment = Alignment.CenterStart,
				) {
					val subTitle = if (title.length > 38) title.substring(0, 38).trim() + ".." else title
					val totalText = "$subTitle ($process)".trim()
					Text(text = totalText, maxLines = 1)
				}
				Spacer(modifier = Modifier.weight(1f))
				Box(
					modifier = Modifier.weight(1f).padding(end = 10.dp),
					contentAlignment = Alignment.CenterEnd,
				) {
					Text(text = calcTimes(time.toInt()), maxLines = 1)
				}
			}
		}
		Spacer(Modifier.fillMaxWidth().height(2.dp).background(AppColors.DARK))
	}

	private fun calcTimes(time: Int): String {
		return when {
			time < 60 -> "$time с."
			time >= 60 && time < 60 * 60 -> {
				val min = time / 60
				val sec = time - (min * 60)
				"$min м. $sec c."
			}
			else -> {
				val hour = time / 60 / 60
				val min = time - (hour * 60)
				"$hour ч. $min м."
			}
		}
	}
}