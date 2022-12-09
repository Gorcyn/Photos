package garcia.ludovic.photos.core.network.retrofit

import garcia.ludovic.photos.core.network.model.NetworkPhoto
import retrofit2.http.GET

interface PhotoNetworkApi {

    @GET("/photos")
    suspend fun getPhotoList(): List<NetworkPhoto>
}
