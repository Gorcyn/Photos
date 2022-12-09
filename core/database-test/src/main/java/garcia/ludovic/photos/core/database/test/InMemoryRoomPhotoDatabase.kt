package garcia.ludovic.photos.core.database.test

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import garcia.ludovic.photos.core.database.room.RoomPhotoDatabase

fun inMemoryRoomPhotoDatabase(
    @ApplicationContext context: Context
): RoomPhotoDatabase =
    Room.inMemoryDatabaseBuilder(context, RoomPhotoDatabase::class.java)
        .build()
