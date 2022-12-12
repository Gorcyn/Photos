package garcia.ludovic.photos.core.data.model

import garcia.ludovic.photos.core.database.LocalPhoto
import garcia.ludovic.photos.core.network.model.NetworkPhoto

data class Photo(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

fun NetworkPhoto.asCommon(): Photo =
    Photo(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )

fun LocalPhoto.asCommon(): Photo =
    Photo(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )

fun Photo.asLocal(): LocalPhoto =
    LocalPhoto(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )
