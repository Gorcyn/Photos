package garcia.ludovic.photos.core.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import garcia.ludovic.photos.core.database.room.RoomPhotoDatabase
import garcia.ludovic.photos.core.database.test.inMemoryRoomPhotoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RoomPhotosLocalDataSourceTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private var roomPhotoDatabase: RoomPhotoDatabase = inMemoryRoomPhotoDatabase(context)

    private val photosLocalDataSource: PhotosLocalDataSource
        get() = RoomPhotosLocalDataSource(
            roomPhotoDatabase.photoDao(),
            Dispatchers.Default
        )

    @Before
    fun init() = runTest {
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
        Assert.assertEquals(
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
        Assert.assertNotNull(item)
        Assert.assertEquals(1, item!!.id)
        Assert.assertEquals(1, item.albumId)
        Assert.assertEquals("Photo 1", item.title)
        Assert.assertEquals("url 1", item.url)
        Assert.assertEquals("thumbnailUrl 1", item.thumbnailUrl)
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
        Assert.assertEquals(4, count)

        photosLocalDataSource.delete(1)

        val countNone = photosLocalDataSource.selectAll().first().count()
        Assert.assertEquals(3, countNone)
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
        Assert.assertEquals(4, count)

        photosLocalDataSource.deleteAll()

        val countNone = photosLocalDataSource.selectAll().first().count()
        Assert.assertEquals(0, countNone)
    }
}
