package garcia.ludovic.photos.benchmark

import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import garcia.ludovic.photos.benchmark.scenario.browseAndZoom
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val PACKAGE_NAME = "garcia.ludovic.photos"
private const val ITERATIONS = 5

@RunWith(AndroidJUnit4::class)
class DefaultBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun browseGalleryThenSelectAPhotoThenCheckDetailsThenGoBackToHomeForAFinalScroll() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(StartupTimingMetric()),
        iterations = ITERATIONS,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()

        with(device) {
            browseAndZoom()
        }
    }
}
