package garcia.ludovic.photos.feature.photo.details

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import garcia.ludovic.photos.core.common.HiltComponentActivity
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import garcia.ludovic.photos.feature.photo.details.ui.PhotoDetailsBottomSheet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class PhotoDetailsBottomSheetTest {

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
        lateinit var photoDetailsPhotoUrl: String
        lateinit var photoDetailsPhotoThumbnailUrl: String
        lateinit var photoDetailsPhotoId: String
        lateinit var photoDetailsPhotoAlbumId: String
        composeTestRule.setContent {
            PhotosTheme {
                photoDetailsPhotoUrl = stringResource(id = R.string.photo_details_photo_url)
                photoDetailsPhotoThumbnailUrl = stringResource(id = R.string.photo_details_photo_thumbnail_url)
                photoDetailsPhotoId = stringResource(id = R.string.photo_details_photo_id)
                photoDetailsPhotoAlbumId = stringResource(id = R.string.photo_details_photo_album_id)
                PhotoDetailsBottomSheet(
                    1,
                    1,
                    "Title 1",
                    "url 1",
                    "thumbnailUrl 1"
                )
            }
        }
        composeTestRule.onNodeWithTag("PhotoDetailsBottomSheetTitle", true)
            .assertIsDisplayed()
            .assertTextEquals("Title 1")

        val titleValueMap = listOf(
            photoDetailsPhotoUrl to "url 1",
            photoDetailsPhotoThumbnailUrl to "thumbnailUrl 1",
            photoDetailsPhotoId to "1",
            photoDetailsPhotoAlbumId to "1"
        )
        titleValueMap.forEachIndexed { index, (subTitle, value) ->
            composeTestRule.onAllNodesWithTag("PhotoDetailsBottomSheetSubTitle", true)[index]
                .assertIsDisplayed()
                .assertTextEquals(subTitle)
            composeTestRule.onAllNodesWithTag("PhotoDetailsBottomSheetValue", true)[index]
                .assertIsDisplayed()
                .assertTextEquals(value)
        }
    }
}
