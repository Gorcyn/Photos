package garcia.ludovic.photos.feature.gallery

import android.provider.ContactsContract.Contacts.Photo
import garcia.ludovic.photos.feature.gallery.model.DisplayStyle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GalleryUiStateTest {

    @Test
    fun defaults_to_display_style_four_and_empty_list_and_without_refreshing_and_without_error() {
        val default = GalleryUiState()

        assertEquals(DisplayStyle.Four, default.displayStyle)
        assertEquals(emptyList<Photo>(), default.photoList)
        assertNull(default.isRefreshing)
        assertNull(default.error)
    }
}
