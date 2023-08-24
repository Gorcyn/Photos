package garcia.ludovic.photos.core.database.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [RoomPhoto::class]
)
abstract class RoomPhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): RoomPhotoDao
}
