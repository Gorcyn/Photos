package garcia.ludovic.photos.core.network.test

import garcia.ludovic.photos.core.network.model.NetworkPhoto
import garcia.ludovic.photos.core.network.retrofit.PhotoNetworkApi

class TestPhotoNewsApi : PhotoNetworkApi {

    override suspend fun getPhotoList(): List<NetworkPhoto> = listOf(
        NetworkPhoto(id = 1, albumId = 1, title = "Photo 1", url = "url 1", thumbnailUrl = "thumbnailUrl 1"),
        NetworkPhoto(id = 2, albumId = 1, title = "Photo 2", url = "url 2", thumbnailUrl = "thumbnailUrl 2"),
        NetworkPhoto(id = 4, albumId = 1, title = "Photo 4", url = "url 4", thumbnailUrl = "thumbnailUrl 4"),
        NetworkPhoto(id = 3, albumId = 1, title = "Photo 3", url = "url 3", thumbnailUrl = "thumbnailUrl 3")
    )
}
