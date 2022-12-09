package garcia.ludovic.photos.core.database.test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import garcia.ludovic.photos.core.database.room.RoomPhoto
import garcia.ludovic.photos.core.database.room.RoomPhotoDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InMemoryRoomPhotoDatabaseTest {

    private lateinit var inMemoryRoomPhotoDatabase: RoomPhotoDatabase

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun no_data_on_new_instance() = runTest {
        inMemoryRoomPhotoDatabase = inMemoryRoomPhotoDatabase(context)
        inMemoryRoomPhotoDatabase.photoDao().save(RoomPhoto(1, 1, "Photo 1", "url 1", "thumbnailUrl"))

        assertEquals(
            1,
            inMemoryRoomPhotoDatabase.photoDao().selectAll().first().count()
        )

        inMemoryRoomPhotoDatabase = inMemoryRoomPhotoDatabase(context)
        assertEquals(
            0,
            inMemoryRoomPhotoDatabase.photoDao().selectAll().first().count()
        )
    }
}
