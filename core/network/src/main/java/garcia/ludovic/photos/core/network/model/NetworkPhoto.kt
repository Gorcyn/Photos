package garcia.ludovic.photos.core.network.model

@kotlinx.serialization.Serializable
data class NetworkPhoto(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)
