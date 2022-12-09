package garcia.ludovic.photos.core.database.test.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import garcia.ludovic.photos.core.database.LocalPhotoQueries
import garcia.ludovic.photos.core.database.PhotosDatabase
import garcia.ludovic.photos.core.database.PhotosLocalDataSource
import garcia.ludovic.photos.core.database.di.DatabaseModule
import garcia.ludovic.photos.core.database.room.RoomPhotoDao
import garcia.ludovic.photos.core.database.room.RoomPhotoDatabase
import garcia.ludovic.photos.core.database.test.InMemoryPhotosDatabase
import garcia.ludovic.photos.core.database.test.TestPhotosLocalDataSource
import garcia.ludovic.photos.core.database.test.inMemoryRoomPhotoDatabase
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestDatabaseModule {

    // region SQLDelight
    @Provides
    @Singleton
    fun providesPhotosDatabase(): PhotosDatabase =
        InMemoryPhotosDatabase()

    @Provides
    @Singleton
    fun providesProjectQueries(
        photosDatabase: PhotosDatabase
    ): LocalPhotoQueries = photosDatabase.localPhotoQueries
    // endregion

    // region Room
    @Provides
    @Singleton
    fun providesRoomPhotoDatabase(
        @ApplicationContext context: Context
    ): RoomPhotoDatabase =
        inMemoryRoomPhotoDatabase(context)

    @Provides
    @Singleton
    fun providesRoomPhotoDao(
        roomPhotoDatabase: RoomPhotoDatabase
    ): RoomPhotoDao = roomPhotoDatabase.photoDao()
    // endregion

    @Provides
    @Singleton
    fun providesPhotosLocalDataSource(): PhotosLocalDataSource = TestPhotosLocalDataSource()
}
