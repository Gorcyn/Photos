package garcia.ludovic.photos.feature.gallery.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import garcia.ludovic.photos.core.common.HiltComponentActivity
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class PhotoItemTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun shows_image() = runTest {
        composeTestRule.setContent {
            PhotosTheme {
                PhotoItem(
                    painter = ColorPainter(Color.LightGray),
                    title = "Photo title"
                ) {}
            }
        }

        composeTestRule.onNodeWithTag("PhotoImage", true)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Photo title")
    }
}
