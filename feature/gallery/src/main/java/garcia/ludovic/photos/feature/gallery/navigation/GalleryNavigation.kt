package garcia.ludovic.photos.feature.gallery.navigation

import androidx.navigation.NavGraphBuilder
import garcia.ludovic.photos.core.design.animation.animatedComposable
import garcia.ludovic.photos.feature.gallery.GalleryRoute

const val galleryNavigationRoute = "gallery_route"

fun NavGraphBuilder.galleryRoute(
    navigateToPhotoDetails: (photoId: Int) -> Unit
) {
    animatedComposable(route = galleryNavigationRoute) {
        GalleryRoute(
            onClick = navigateToPhotoDetails
        )
    }
}
