package garcia.ludovic.photos.core.data

import garcia.ludovic.photos.core.data.exception.DataSyncException
import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.core.data.test.util.TestNetworkMonitor
import garcia.ludovic.photos.core.database.LocalPhoto
import garcia.ludovic.photos.core.database.test.TestPhotosLocalDataSource
import garcia.ludovic.photos.core.network.test.TestPhotoNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultPhotoRepositoryTest {

    private var photoNetworkDataSource = TestPhotoNetworkDataSource()

    private var photosLocalDataSource = TestPhotosLocalDataSource()

    private var networkMonitor = TestNetworkMonitor()

    private lateinit var photoRepository: PhotoRepository

    @Before
    fun init() = runTest {
        photoNetworkDataSource = TestPhotoNetworkDataSource()
        photosLocalDataSource = TestPhotosLocalDataSource()
        networkMonitor = TestNetworkMonitor()
        photoRepository = DefaultPhotoRepository(
            photoNetworkDataSource,
            photosLocalDataSource,
            Dispatchers.Default,
            networkMonitor
        )

        networkMonitor.isOnline = true
        photosLocalDataSource.deleteAll()
    }

    @Test
    fun getPhotoList_gets_empty_list_when_not_synced() = runTest {
        // When empty database
        photosLocalDataSource.deleteAll()

        // Get Photo list
        val photoList = photoRepository.getPhotoList().first()
        val expected = emptyList<Photo>()
        assertEquals(expected, photoList)

        // Validate database is still empty
        val localList = photosLocalDataSource.selectAll().first()
        val expectedLocalList = emptyList<Photo>()
        assertEquals(expectedLocalList, localList)
    }

    @Test
    fun sync_save_new_items_and_update_existing_ones_when_some_in_local() = runTest {
        // Populate database with some items
        val toSave = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2")
        )
        toSave.forEach { photosLocalDataSource.save(it) }

        // Validate database is not empty
        val localCount = photosLocalDataSource.selectAll().first().count()
        assertEquals(2, localCount)

        photoRepository.sync()

        // Get Photo list
        val photoList = photoRepository.getPhotoList().first()
        val expectedPhotoList = listOf(
            Photo(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            Photo(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
            Photo(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3"),
            Photo(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4")
        )
        assertEquals(expectedPhotoList, photoList)

        // Validate database contains all items
        val localList = photosLocalDataSource.selectAll().first()
        val expectedLocalList = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
            LocalPhoto(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3"),
            LocalPhoto(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4")
        )
        assertEquals(expectedLocalList, localList)
    }

    @Test
    fun sync_deletes_obsoletes_from_local() = runTest {
        // Populate database with some obsolete items
        val toSave = listOf(
            LocalPhoto(id = 5, albumId = 5, title = "Photo 5", url = "url 5", thumbnailUrl = "thumbnailUrl 5"),
            LocalPhoto(id = 6, albumId = 6, title = "Photo 6", url = "url 6", thumbnailUrl = "thumbnailUrl 6")
        )
        toSave.forEach { photosLocalDataSource.save(it) }

        // Validate database is not empty
        val localCount = photosLocalDataSource.selectAll().first().count()
        assertEquals(2, localCount)

        photoRepository.sync()

        // Get Photo list
        val photoList = photoRepository.getPhotoList().first()
        val expectedPhotoList = listOf(
            Photo(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            Photo(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
            Photo(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3"),
            Photo(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4")
        )
        assertEquals(expectedPhotoList, photoList)

        // Validate database contains all new items and no obsolete
        val localList = photosLocalDataSource.selectAll().first()
        val expectedLocalList = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
            LocalPhoto(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3"),
            LocalPhoto(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4")
        )
        assertEquals(expectedLocalList, localList)
    }

    @Test
    fun getPhotoList_throws_exception_when_remote_fails() = runTest {
        // Remote will throw: server or network error
        photoNetworkDataSource.willThrow = Exception("Any network exception")

        try {
            photoRepository.getPhotoList().first()
        } catch (e: Throwable) {
            assertTrue(e is DataSyncException)
        }
    }

    @Test
    fun getPhotoList_retrieves_list_from_local() = runTest {
        // Network is not available
        networkMonitor.isOnline = false

        // Populate database with some items
        val toSave = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2")
        )
        toSave.forEach { photosLocalDataSource.save(it) }

        // Validate database is not empty
        val localList = photosLocalDataSource.selectAll().first()
        val expectedLocalList = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2")
        )
        assertEquals(expectedLocalList, localList)

        // Get Photo list
        val photoList = photoRepository.getPhotoList().first()
        val expectedList = listOf(
            Photo(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            Photo(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2")
        )
        assertEquals(expectedList, photoList)

        // Validate database was not altered
        val localListUnchanged = photosLocalDataSource.selectAll().first()
        val expectedLocalListUnchanged = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2")
        )
        assertEquals(expectedLocalListUnchanged, localListUnchanged)
    }

    @Test
    fun getPhoto_retrieves_photo_from_local() = runTest {
        // Network is not available
        networkMonitor.isOnline = false

        // Populate database with some items
        val toSave = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2")
        )
        toSave.forEach { photosLocalDataSource.save(it) }

        // Validate database is not empty
        val localList = photosLocalDataSource.selectAll().first()
        val expectedLocalList = listOf(
            LocalPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
            LocalPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2")
        )
        assertEquals(expectedLocalList, localList)

        // Get Photo
        val photoList = photoRepository.getPhoto(2)
        val expectedPhoto = Photo(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2")
        assertEquals(expectedPhoto, photoList)
    }

    @Test
    fun getPhoto_returns_null_when_not_found() = runTest {
        // Validate database is empty
        val localCount = photosLocalDataSource.selectAll().first().count()
        assertEquals(0, localCount)

        // Get Photo
        val photo = photoRepository.getPhoto(2)
        assertNull(photo)
    }
}
