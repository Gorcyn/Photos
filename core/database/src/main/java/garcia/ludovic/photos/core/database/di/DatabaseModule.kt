package garcia.ludovic.photos.core.database.di

import android.content.Context
import androidx.room.Room
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import garcia.ludovic.photos.core.common.Dispatcher
import garcia.ludovic.photos.core.common.PhotosDispatchers
import garcia.ludovic.photos.core.database.DefaultPhotosLocalDataSource
import garcia.ludovic.photos.core.database.LocalPhoto
import garcia.ludovic.photos.core.database.LocalPhotoQueries
import garcia.ludovic.photos.core.database.PhotosDatabase
import garcia.ludovic.photos.core.database.PhotosLocalDataSource
import garcia.ludovic.photos.core.database.RoomPhotosLocalDataSource
import garcia.ludovic.photos.core.database.room.RoomPhotoDao
import garcia.ludovic.photos.core.database.room.RoomPhotoDatabase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

// true: Room
// false: SqlDelight
private const val TO_ROOM_OR_NOT_TO_ROOM = false

private const val DATABASE_NAME = "photos"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // region SQLDelight
    @Provides
    @Singleton
    fun providesSqlDriver(
        @ApplicationContext context: Context
    ): SqlDriver = AndroidSqliteDriver(PhotosDatabase.Schema, context, "$DATABASE_NAME.db")

    @Provides
    @Singleton
    fun providesPhotosDatabase(
        sqlDriver: SqlDriver
    ): PhotosDatabase = PhotosDatabase(
        driver = sqlDriver,
        localPhotoAdapter = LocalPhoto.Adapter(
            idAdapter = IntColumnAdapter,
            albumIdAdapter = IntColumnAdapter
        )
    )

    @Provides
    @Singleton
    fun providesLocalPhotoQueries(
        photosDatabase: PhotosDatabase
    ): LocalPhotoQueries = photosDatabase.localPhotoQueries
    // endregion

    // region Room
    @Provides
    @Singleton
    fun providesRoomPhotoDatabase(
        @ApplicationContext context: Context
    ): RoomPhotoDatabase = Room.databaseBuilder(
        context,
        RoomPhotoDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providesRoomPhotoDao(
        roomPhotoDatabase: RoomPhotoDatabase
    ): RoomPhotoDao = roomPhotoDatabase.photoDao()
    // endregion

    @Provides
    @Singleton
    fun providesPhotosLocalDataSource(
        roomPhotoDao: RoomPhotoDao,
        localPhotoQueries: LocalPhotoQueries,
        @Dispatcher(PhotosDispatchers.IO) defaultDispatcher: CoroutineDispatcher
    ): PhotosLocalDataSource = if (TO_ROOM_OR_NOT_TO_ROOM) {
        RoomPhotosLocalDataSource(roomPhotoDao, defaultDispatcher)
    } else {
        DefaultPhotosLocalDataSource(localPhotoQueries, defaultDispatcher)
    }
}
