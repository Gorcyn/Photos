package garcia.ludovic.photos.core.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import garcia.ludovic.photos.core.common.Dispatcher
import garcia.ludovic.photos.core.common.PhotosDispatchers.IO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPhotosLocalDataSource @Inject constructor(
    private val localPhotoQueries: LocalPhotoQueries,
    @Dispatcher(IO) private val defaultDispatcher: CoroutineDispatcher
) : PhotosLocalDataSource {

    override fun selectAll(): Flow<List<LocalPhoto>> =
        localPhotoQueries.selectAll()
            .asFlow()
            .mapToList(defaultDispatcher)

    override suspend fun select(id: Int): LocalPhoto? =
        localPhotoQueries.select(id)
            .executeAsOneOrNull()

    override suspend fun save(localPhoto: LocalPhoto) =
        localPhotoQueries.save(localPhoto)

    override suspend fun deleteAll() =
        localPhotoQueries.deleteAll()

    override suspend fun delete(id: Int) =
        localPhotoQueries.delete(id)

    override suspend fun transaction(body: suspend PhotosLocalDataSource.() -> Unit) =
        withContext(defaultDispatcher) {
            localPhotoQueries.transaction {
                this@withContext.launch { body() }
            }
        }
}
