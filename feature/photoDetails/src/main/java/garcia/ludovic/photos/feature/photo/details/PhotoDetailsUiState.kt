package garcia.ludovic.photos.feature.photo.details

import garcia.ludovic.photos.core.data.model.Photo

data class PhotoDetailsUiState(
    val photoId: Int,
    val photo: Photo? = null,
    val isRefreshing: Boolean = true,
    val error: Throwable? = null
)
