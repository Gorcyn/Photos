package garcia.ludovic.photos.benchmark

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import garcia.ludovic.photos.benchmark.scenario.browseAndZoom
import org.junit.Rule
import org.junit.Test

private const val PACKAGE_NAME = "garcia.ludovic.photos"

@OptIn(ExperimentalBaselineProfilesApi::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun browseGalleryThenSelectAPhotoThenCheckDetailsThenGoBackToHomeForAFinalScroll() {
        baselineProfileRule.collectBaselineProfile(packageName = PACKAGE_NAME) {
            // App startup journey
            startActivityAndWait()

            with(device) {
                browseAndZoom()
            }
        }
    }
}
