package garcia.ludovic.photos.core.database.test

import garcia.ludovic.photos.core.database.LocalPhoto
import garcia.ludovic.photos.core.database.PhotosLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestPhotosLocalDataSource @Inject constructor() : PhotosLocalDataSource {

    private var photoList = mutableListOf<LocalPhoto>()
    private val photoListFlow = MutableStateFlow(photoList)

    override fun selectAll(): Flow<List<LocalPhoto>> =
        photoListFlow.map {
            it.sortedBy { photo -> photo.id }
        }

    override suspend fun select(id: Int): LocalPhoto? =
        photoList.firstOrNull { it.id == id }

    override suspend fun save(localPhoto: LocalPhoto) {
        photoList.add(localPhoto)
        photoListFlow.tryEmit(photoList)
    }

    override suspend fun deleteAll() {
        photoList = mutableListOf()
        photoListFlow.tryEmit(photoList)
    }

    override suspend fun delete(id: Int) {
        photoList.removeAt(photoList.indexOfFirst { it.id == id })
        photoListFlow.tryEmit(photoList)
    }
}
