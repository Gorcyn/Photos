package garcia.ludovic.photos.core.data.test

import garcia.ludovic.photos.core.data.PhotoRepository
import garcia.ludovic.photos.core.data.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TestPhotoRepository @Inject constructor() : PhotoRepository {

    private var photoList = mutableListOf(
        Photo(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
        Photo(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
        Photo(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3"),
        Photo(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4")
    )
    private val photoListFlow = MutableStateFlow(photoList)

    var willThrow: Throwable? = null

    override fun getPhotoList(): Flow<List<Photo>> =
        photoListFlow

    override suspend fun sync() {
        willThrow?.let {
            throw it
        }
        photoListFlow.tryEmit(photoList)
    }

    override suspend fun getPhoto(id: Int): Photo? =
        photoList.firstOrNull { it.id == id }

    fun delete(id: Int) {
        photoList = photoList.filter { it.id != id }.toMutableList()
        photoListFlow.tryEmit(photoList)
    }

    fun add(photo: Photo) {
        photoList.add(photo)
        photoListFlow.tryEmit(photoList)
    }
}
