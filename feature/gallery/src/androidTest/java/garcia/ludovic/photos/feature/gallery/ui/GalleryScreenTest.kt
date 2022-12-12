package garcia.ludovic.photos.feature.gallery.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTopPositionInRootIsEqualTo
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.dp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import garcia.ludovic.photos.core.common.HiltComponentActivity
import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import garcia.ludovic.photos.feature.gallery.R
import garcia.ludovic.photos.feature.gallery.model.DisplayStyle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class
)
@HiltAndroidTest
class GalleryScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun shows_grid() = runTest {
        composeTestRule.setContent {
            PhotosTheme {
                GalleryScreen(
                    isRefreshing = false,
                    photoList = listOf(
                        Photo(1, 1, "Photo 1", "url 1", "thumbnailUrl 1"),
                        Photo(2, 1, "Photo 2", "url 2", "thumbnailUrl 2"),
                        Photo(3, 1, "Photo 3", "url 3", "thumbnailUrl 3"),
                        Photo(4, 1, "Photo 4", "url 4", "thumbnailUrl 4")
                    ),
                    onDisplayStyle = {},
                    onClick = {}
                )
            }
        }

        composeTestRule.onAllNodesWithTag("PhotoImage", true)
            .assertCountEquals(4)
        composeTestRule.onAllNodesWithTag("PhotoImage", true)[0]
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Photo 1")
        composeTestRule.onAllNodesWithTag("PhotoImage", true)[1]
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Photo 2")
        composeTestRule.onAllNodesWithTag("PhotoImage", true)[2]
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Photo 3")
        composeTestRule.onAllNodesWithTag("PhotoImage", true)[3]
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Photo 4")
    }

    @Test
    fun binds_clicks() = runTest {
        var clicked: Int? = null

        composeTestRule.setContent {
            PhotosTheme {
                GalleryScreen(
                    isRefreshing = false,
                    photoList = listOf(
                        Photo(1, 1, "Photo 1", "url 1", "thumbnailUrl 1"),
                        Photo(2, 1, "Photo 2", "url 2", "thumbnailUrl 2"),
                        Photo(3, 1, "Photo 3", "url 3", "thumbnailUrl 3"),
                        Photo(4, 1, "Photo 4", "url 4", "thumbnailUrl 4")
                    ),
                    onDisplayStyle = {},
                    onClick = { clicked = it }
                )
            }
        }

        composeTestRule.onAllNodesWithTag("PhotoImage", true)[0]
            .performClick()
        assertEquals(clicked, 1)

        composeTestRule.onAllNodesWithTag("PhotoImage", true)[2]
            .performClick()
        assertEquals(clicked, 3)
    }

    @Test
    fun scrolls_through_screen() = runTest {
        composeTestRule.setContent {
            PhotosTheme {
                GalleryScreen(
                    isRefreshing = false,
                    photoList = (1..80).map {
                        Photo(it, 1, "Photo $it", "url $it", "thumbnailUrl $it")
                    },
                    onDisplayStyle = {},
                    onClick = {}
                )
            }
        }
        composeTestRule.onAllNodesWithContentDescription("Photo 1", true).onFirst()
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("GalleryGrid", true).performTouchInput {
            swipeUp()
        }

        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithContentDescription("Photo 80").fetchSemanticsNodes().size == 1
        }

        composeTestRule.onNodeWithContentDescription("Photo 80", true)
            .assertIsDisplayed()
    }

    @Test
    fun scrolls_down_shows_pull_refresh_indicator() = runTest {
        composeTestRule.setContent {
            PhotosTheme {
                GalleryScreen(
                    isRefreshing = false,
                    photoList = (1..40).map {
                        Photo(it, 1, "Photo $it", "url $it", "thumbnailUrl $it")
                    },
                    onDisplayStyle = {},
                    onClick = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("GalleryGrid", true).performTouchInput {
            swipeDown()
        }
        composeTestRule.onNodeWithTag("GalleryPullRefreshIndicator", true)
            .assertIsDisplayed()
            .assertTopPositionInRootIsEqualTo(64.dp)
    }

    @Test
    fun switches_display_style() = runTest {
        lateinit var twoByTwoContentDescription: String
        var displayStyle: DisplayStyle = DisplayStyle.Four

        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            PhotosTheme {
                twoByTwoContentDescription = stringResource(id = R.string.gallery_display_mode_grid_2)

                GalleryScreen(
                    isRefreshing = false,
                    displayStyle = displayStyle,
                    photoList = listOf(
                        Photo(1, 1, "Photo 1", "url 1", "thumbnailUrl 1"),
                        Photo(2, 1, "Photo 2", "url 2", "thumbnailUrl 2"),
                        Photo(3, 1, "Photo 3", "url 3", "thumbnailUrl 3"),
                        Photo(4, 1, "Photo 4", "url 4", "thumbnailUrl 4")
                    ),
                    onDisplayStyle = {
                        displayStyle = displayStyle.next()
                    },
                    onClick = {}
                )
            }
        }

        composeTestRule.onAllNodesWithTag("GalleryPhotoItem_${DisplayStyle.Four.name}").assertCountEquals(4)
        composeTestRule.onAllNodesWithTag("GalleryPhotoItem_${DisplayStyle.Two.name}").assertCountEquals(0)

        composeTestRule.onNodeWithContentDescription(twoByTwoContentDescription, true)
            .assertIsDisplayed()
            .performClick()

        stateRestorationTester.emulateSavedInstanceStateRestore()
        assertEquals(DisplayStyle.Two, displayStyle)

        composeTestRule.onAllNodesWithTag("GalleryPhotoItem_${DisplayStyle.Four.name}").assertCountEquals(0)
        composeTestRule.onAllNodesWithTag("GalleryPhotoItem_${DisplayStyle.Two.name}").assertCountEquals(4)
    }
}
