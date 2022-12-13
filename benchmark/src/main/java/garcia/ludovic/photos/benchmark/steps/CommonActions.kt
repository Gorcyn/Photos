package garcia.ludovic.photos.benchmark.steps

import androidx.test.uiautomator.UiDevice

private const val WAITING_TIME_BETWEEN_STEPS = 500L

internal fun UiDevice.idle() = waitForIdle(WAITING_TIME_BETWEEN_STEPS)
