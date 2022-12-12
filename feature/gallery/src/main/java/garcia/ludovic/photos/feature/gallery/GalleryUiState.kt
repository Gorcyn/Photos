package garcia.ludovic.photos.feature.gallery

import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.feature.gallery.model.DisplayStyle

data class GalleryUiState(
    val displayStyle: DisplayStyle = DisplayStyle.Four,
    val photoList: List<Photo> = emptyList(),
    val isRefreshing: Boolean? = null,
    val error: Throwable? = null
)
