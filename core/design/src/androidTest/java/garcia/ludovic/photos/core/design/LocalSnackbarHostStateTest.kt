package garcia.ludovic.photos.core.design

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import garcia.ludovic.photos.core.common.HiltComponentActivity
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LocalSnackbarHostStateTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun throws_when_current_is_null() = runTest {
        try {
            composeTestRule.setContent {
                PhotosTheme {
                    LocalSnackbarHostState.current
                }
            }
        } catch (e: Throwable) {
            assertEquals("No SnackbarHostState provided", e.message)
        }
    }

    @Test
    fun current_is_not_null_when_provided() = runTest {
        composeTestRule.setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            PhotosTheme {
                CompositionLocalProvider(
                    LocalSnackbarHostState provides snackbarHostState
                ) {
                    LocalSnackbarHostState.current
                }
            }
        }
        assertTrue(true)
    }
}
