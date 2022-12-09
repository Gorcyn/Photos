package garcia.ludovic.photos.core.database.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomPhoto::class], version = 1)
abstract class RoomPhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): RoomPhotoDao
}
