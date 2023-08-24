package garcia.ludovic.photos.core.design.ui

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import garcia.ludovic.photos.core.common.HiltComponentActivity
import garcia.ludovic.photos.core.design.test.assertBackgroundColor
import garcia.ludovic.photos.core.design.test.assertTextColor
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
@HiltAndroidTest
class PhotosTopAppBarTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun shows_title() = runTest {
        composeTestRule.setContent {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            PhotosTheme {
                PhotosTopAppBar("Title", scrollBehavior)
            }
        }
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
    }

    @Test
    fun shows_title_and_actions() = runTest {
        composeTestRule.setContent {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            PhotosTheme {
                PhotosTopAppBar("Title", scrollBehavior) {
                    Text("Action")
                }
            }
        }
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Action").assertIsDisplayed()
    }

    @Test
    fun uses_theme_colors() = runTest {
        lateinit var colorScheme: ColorScheme

        composeTestRule.setContent {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            PhotosTheme {
                colorScheme = PhotosTheme.colorScheme

                PhotosTopAppBar("Title", scrollBehavior) {
                    Text("Action")
                }
            }
        }

        composeTestRule.onNodeWithTag("PhotosTopAppBar", true)
            .assertBackgroundColor(colorScheme.background)
        composeTestRule.onNodeWithText("Title")
            .assertTextColor(colorScheme.onBackground)
        composeTestRule.onNodeWithText("Action")
            .assertTextColor(colorScheme.onBackground)
    }
}
