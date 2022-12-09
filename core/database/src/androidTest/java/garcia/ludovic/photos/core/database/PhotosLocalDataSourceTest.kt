package garcia.ludovic.photos.core.database

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class PhotosLocalDataSourceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var photosLocalDataSource: PhotosLocalDataSource

    @Before
    fun init() = runTest {
        hiltRule.inject()
        photosLocalDataSource.deleteAll()
    }

    @Test
    fun selects_items_by_ascending_id() = runTest {
        val photoList = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
            LocalPhoto(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4"),
            LocalPhoto(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3")
        )
        photoList.forEach { photosLocalDataSource.save(it) }

        val items = photosLocalDataSource.selectAll().first()
        assertEquals(
            listOf(
                1 to "Photo 1",
                2 to "Photo 2",
                3 to "Photo 3",
                4 to "Photo 4"
            ),
            items.map { it.id to it.title }
        )
    }

    @Test
    fun selects_item_by_id() = runTest {
        photosLocalDataSource.save(LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"))

        val item = photosLocalDataSource.select(1)
        assertNotNull(item)
        assertEquals(1, item!!.id)
        assertEquals(1, item.albumId)
        assertEquals("Photo 1", item.title)
        assertEquals("url 1", item.url)
        assertEquals("thumbnailUrl 1", item.thumbnailUrl)
    }

    @Test
    fun deletes_item_by_id() = runTest {
        val photoList = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
            LocalPhoto(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3"),
            LocalPhoto(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4")
        )
        photoList.forEach { photosLocalDataSource.save(it) }

        val count = photosLocalDataSource.selectAll().first().count()
        assertEquals(4, count)

        photosLocalDataSource.delete(1)

        val countNone = photosLocalDataSource.selectAll().first().count()
        assertEquals(3, countNone)
    }

    @Test
    fun deletes_items() = runTest {
        val photoList = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
            LocalPhoto(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3"),
            LocalPhoto(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4")
        )
        photoList.forEach { photosLocalDataSource.save(it) }

        val count = photosLocalDataSource.selectAll().first().count()
        assertEquals(4, count)

        photosLocalDataSource.deleteAll()

        val countNone = photosLocalDataSource.selectAll().first().count()
        assertEquals(0, countNone)
    }
}
