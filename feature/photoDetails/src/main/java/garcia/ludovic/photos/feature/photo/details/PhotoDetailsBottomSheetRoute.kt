package garcia.ludovic.photos.feature.photo.details

import androidx.compose.runtime.Composable
import garcia.ludovic.photos.feature.photo.details.ui.PhotoDetailsBottomSheet

@Composable
fun PhotoDetailsBottomSheetRoute(
    id: Int,
    albumId: Int,
    title: String,
    url: String,
    thumbnailUrl: String
) {
    PhotoDetailsBottomSheet(id, albumId, title, url, thumbnailUrl)
}
