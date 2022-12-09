package garcia.ludovic.photos.core.network

import garcia.ludovic.photos.core.network.model.NetworkPhoto
import garcia.ludovic.photos.core.network.retrofit.PhotoNetworkApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPhotoNetworkDataSource @Inject constructor(
    private val photoNetworkApi: PhotoNetworkApi
) : PhotoNetworkDataSource {

    override suspend fun getPhotoList(): List<NetworkPhoto> =
        photoNetworkApi.getPhotoList().sortedBy { it.id }
}
