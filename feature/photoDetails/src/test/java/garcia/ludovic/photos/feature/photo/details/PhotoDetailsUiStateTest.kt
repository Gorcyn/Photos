package garcia.ludovic.photos.feature.photo.details

import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PhotoDetailsUiStateTest {

    @Test
    fun defaults_to_null_photo_and_is_refreshing_and_without_error() {
        val default = PhotoDetailsUiState(1)

        assertNull(default.photo)
        assertTrue(default.isRefreshing)
        assertTrue(default.isRefreshing)
        assertNull(default.error)
    }
}
