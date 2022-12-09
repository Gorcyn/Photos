package garcia.ludovic.photos.core.network

import garcia.ludovic.photos.core.network.model.NetworkPhoto

interface PhotoNetworkDataSource {
    suspend fun getPhotoList(): List<NetworkPhoto>
}
