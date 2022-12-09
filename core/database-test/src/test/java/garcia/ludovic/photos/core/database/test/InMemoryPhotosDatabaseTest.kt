package garcia.ludovic.photos.core.database.test

import garcia.ludovic.photos.core.database.LocalPhoto
import org.junit.Assert.assertEquals
import org.junit.Test

class InMemoryPhotosDatabaseTest {

    private lateinit var inMemoryPhotosDatabase: InMemoryPhotosDatabase

    @Test
    fun no_data_on_new_instance() {
        inMemoryPhotosDatabase = InMemoryPhotosDatabase()
        inMemoryPhotosDatabase.localPhotoQueries.save(LocalPhoto(1, 1, "Photo 1", "url 1", "thumbnailUrl"))

        assertEquals(1, inMemoryPhotosDatabase.localPhotoQueries.selectAll().executeAsList().count())

        inMemoryPhotosDatabase = InMemoryPhotosDatabase()
        assertEquals(0, inMemoryPhotosDatabase.localPhotoQueries.selectAll().executeAsList().count())
    }
}
