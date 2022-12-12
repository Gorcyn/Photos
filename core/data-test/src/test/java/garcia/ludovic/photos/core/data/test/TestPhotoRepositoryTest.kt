package garcia.ludovic.photos.core.data.test

import garcia.ludovic.photos.core.data.model.Photo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TestPhotoRepositoryTest {

    private val photoRepository = TestPhotoRepository()

    @Before
    fun init() {
        photoRepository.willThrow = null
    }

    @Test
    fun getPhotoList_retrieves_photo_list() = runTest {
        val photoList = photoRepository.getPhotoList().first()
        val expected = listOf(
            Photo(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            Photo(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
            Photo(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3"),
            Photo(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4")
        )
        assertEquals(photoList, expected)
    }

    @Test
    fun sync_throws_when_willThrow_is_provided() = runTest {
        photoRepository.willThrow = Exception("Network error.")

        try {
            photoRepository.sync()
        } catch (e: Throwable) {
            assertTrue(e is Exception)
            assertEquals("Network error.", e.message)
        }
    }

    @Test
    fun sync_does_not_throw_when_willThrow_is_null() = runTest {
        photoRepository.sync()
        assertTrue(true)
    }

    @Test
    fun getPhoto_returns_item() = runTest {
        val photo = photoRepository.getPhoto(2)
        val expected = Photo(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2")
        assertEquals(expected, photo)
    }

    @Test
    fun getPhoto_returns_null_when_not_found() = runTest {
        val photo = photoRepository.getPhoto(5)
        assertNull(photo)
    }
}
