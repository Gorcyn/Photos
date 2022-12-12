package garcia.ludovic.photos.core.design

import androidx.compose.foundation.clickable
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import garcia.ludovic.photos.core.common.HiltComponentActivity
import garcia.ludovic.photos.core.design.animation.animatedComposable
import garcia.ludovic.photos.core.design.test.assertBackgroundColor
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class PhotosAppTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun shows_screen() = runTest {
        composeTestRule.setContent {
            PhotosApp("FirstScreen") {
                animatedComposable("FirstScreen") {
                    Text(
                        "FirstScreen content",
                        color = PhotosTheme.colorScheme.onBackground
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("FirstScreen content").assertIsDisplayed()
    }

    @Test
    fun navigate_to_second_screen() = runTest {
        composeTestRule.setContent {
            PhotosApp("FirstScreen") { navController ->
                animatedComposable("FirstScreen") {
                    Text(
                        "FirstScreen content",
                        color = PhotosTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable {
                            navController.navigate("SecondScreen")
                        }
                    )
                }
                animatedComposable("SecondScreen") {
                    Text(
                        "SecondScreen content",
                        color = PhotosTheme.colorScheme.onBackground
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("FirstScreen content").performClick()
        composeTestRule.onNodeWithText("SecondScreen content").assertIsDisplayed()
    }

    @Test
    fun navigate_to_second_screen_then_back_to_first() = runTest {
        composeTestRule.setContent {
            PhotosApp("FirstScreen") { navController ->
                animatedComposable("FirstScreen") {
                    Text(
                        "FirstScreen content",
                        color = PhotosTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable {
                            navController.navigate("SecondScreen")
                        }
                    )
                }
                animatedComposable("SecondScreen") {
                    Text(
                        "SecondScreen content",
                        color = PhotosTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("FirstScreen content").performClick()
        composeTestRule.onNodeWithText("SecondScreen content").assertIsDisplayed()

        composeTestRule.onNodeWithText("SecondScreen content").performClick()
        composeTestRule.onNodeWithText("FirstScreen content").assertIsDisplayed()
    }

    @Test
    fun uses_theme_colors() = runTest {
        lateinit var colorScheme: ColorScheme

        composeTestRule.setContent {
            PhotosTheme {
                colorScheme = PhotosTheme.colorScheme

                PhotosApp("FirstScreen") {
                    animatedComposable("FirstScreen") {
                        Text(
                            "FirstScreen content",
                            color = PhotosTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }

        composeTestRule.onNodeWithTag("Scaffold", true)
            .assertBackgroundColor(colorScheme.background)
    }
}
