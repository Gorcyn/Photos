package garcia.ludovic.photos.core.network.test

import garcia.ludovic.photos.core.network.model.NetworkPhoto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class TestPhotoNetworkDataSourceTest {

    private val photoNetworkDataSource = TestPhotoNetworkDataSource()

    @Test
    fun getPhotoList_retrieves_a_sorted_list() = runTest {
        val photoList = photoNetworkDataSource.getPhotoList()
        val expected = listOf(
            NetworkPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            NetworkPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
            NetworkPhoto(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3"),
            NetworkPhoto(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4")
        )

        assertEquals(expected, photoList)
    }
}
