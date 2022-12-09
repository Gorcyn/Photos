package garcia.ludovic.photos.core.database

import androidx.room.Transaction
import garcia.ludovic.photos.core.common.Dispatcher
import garcia.ludovic.photos.core.common.PhotosDispatchers
import garcia.ludovic.photos.core.database.room.RoomPhoto
import garcia.ludovic.photos.core.database.room.RoomPhotoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomPhotosLocalDataSource @Inject constructor(
    private val roomPhotoDao: RoomPhotoDao,
    @Dispatcher(PhotosDispatchers.IO) private val defaultDispatcher: CoroutineDispatcher
) : PhotosLocalDataSource {

    private fun RoomPhoto.asLocal(): LocalPhoto =
        LocalPhoto(id, albumId, title, url, thumbnailUrl)

    private fun LocalPhoto.asRoom(): RoomPhoto =
        RoomPhoto(id, albumId, title, url, thumbnailUrl)

    override fun selectAll(): Flow<List<LocalPhoto>> =
        roomPhotoDao.selectAll()
            .map { list ->
                withContext(defaultDispatcher) {
                    list.map { it.asLocal() }
                }
            }

    override suspend fun select(id: Int): LocalPhoto? =
        roomPhotoDao.select(id)
            ?.asLocal()

    override suspend fun save(localPhoto: LocalPhoto) =
        roomPhotoDao.save(localPhoto.asRoom())

    override suspend fun deleteAll() =
        roomPhotoDao.deleteAll()

    override suspend fun delete(id: Int) =
        roomPhotoDao.delete(id)

    @Transaction
    override suspend fun transaction(body: suspend PhotosLocalDataSource.() -> Unit) = body()
}
