package garcia.ludovic.photos.core.database.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localPhoto")
data class RoomPhoto(
    @PrimaryKey val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
