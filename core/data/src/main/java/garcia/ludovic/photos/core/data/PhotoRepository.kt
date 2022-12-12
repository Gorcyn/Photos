package garcia.ludovic.photos.core.data

import garcia.ludovic.photos.core.data.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun getPhotoList(): Flow<List<Photo>>

    suspend fun sync()

    suspend fun getPhoto(id: Int): Photo?
}
