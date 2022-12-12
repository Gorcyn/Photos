package garcia.ludovic.photos.feature.photo.details

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import garcia.ludovic.photos.core.common.HiltComponentActivity
import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import garcia.ludovic.photos.feature.photo.details.ui.PhotoDetailsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class PhotoDetailsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun shows_details() = runTest {
        composeTestRule.setContent {
            PhotosTheme {
                PhotoDetailsScreen(
                    1,
                    null
                )
            }
        }
        composeTestRule.onNodeWithTag("PhotoDetailsImage", true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("PhotoDetailsIcon", true)
            .assertDoesNotExist()
    }

    @Test
    fun shows_details_with_photo() = runTest {
        lateinit var photoDetailsInfoDescription: String

        composeTestRule.setContent {
            photoDetailsInfoDescription = stringResource(id = R.string.photo_details_info_description)

            PhotosTheme {
                PhotoDetailsScreen(
                    1,
                    Photo(1, 1, "Title 1", "url 1", "thumbnailUrl 1")
                )
            }
        }
        composeTestRule.onNodeWithTag("PhotoDetailsImage", true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("PhotoDetailsIcon", true)
            .assertIsDisplayed()
            .assertContentDescriptionEquals(photoDetailsInfoDescription)
    }
}
