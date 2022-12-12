package garcia.ludovic.photos.core.data

import garcia.ludovic.photos.core.common.Dispatcher
import garcia.ludovic.photos.core.common.PhotosDispatchers.IO
import garcia.ludovic.photos.core.data.exception.DataSyncException
import garcia.ludovic.photos.core.data.exception.OfflineException
import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.core.data.model.asCommon
import garcia.ludovic.photos.core.data.model.asLocal
import garcia.ludovic.photos.core.data.util.NetworkMonitor
import garcia.ludovic.photos.core.database.PhotosLocalDataSource
import garcia.ludovic.photos.core.network.PhotoNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPhotoRepository @Inject constructor(
    private val photosNetworkDataSource: PhotoNetworkDataSource,
    private val photosLocalDataSource: PhotosLocalDataSource,
    @Dispatcher(IO) private val defaultDispatcher: CoroutineDispatcher,
    private val networkMonitor: NetworkMonitor
) : PhotoRepository {

    // Get the list from local source
    // Flow will be updated when syncing
    override fun getPhotoList(): Flow<List<Photo>> =
        photosLocalDataSource.selectAll()
            .map { it.map { photo -> photo.asCommon() } }

    override suspend fun sync(): Unit = withContext(defaultDispatcher) {
        // If network is not available, interrupt
        if (!networkMonitor.isOnline()) {
            throw OfflineException()
        }

        try {
            // Let's concurrently get remote and local lists
            val remoteListAsync = async {
                photosNetworkDataSource.getPhotoList()
                    .map { it.asCommon() }
            }
            val localListAsync = async {
                getPhotoList().firstOrNull()
                    ?: emptyList()
            }
            val remoteList = remoteListAsync.await()
            val localList = localListAsync.await()

            // When nothing from remote, just stop there
            if (remoteList.isEmpty()) {
                return@withContext
            }

            // Update local database if diff between local and remote
            photosLocalDataSource.transaction {
                // Delete from local when no longer on remote
                val obsolete = localList.minus(remoteList.toSet())
                obsolete.forEach { photosLocalDataSource.delete(it.id) }

                // Save from remote when not yet in local
                val new = remoteList.minus(localList.minus(obsolete.toSet()).toSet())
                new.forEach { photosLocalDataSource.save(it.asLocal()) }
            }
        } catch (e: Throwable) {
            throw DataSyncException(e)
        }
    }

    // The photo is in local or does not exist
    override suspend fun getPhoto(id: Int): Photo? =
        photosLocalDataSource.select(id)?.asCommon()
}
