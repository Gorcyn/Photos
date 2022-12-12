package garcia.ludovic.photos.feature.photo.details.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import garcia.ludovic.photos.core.design.animation.animatedComposable
import garcia.ludovic.photos.feature.photo.details.PhotoDetailsBottomSheetRoute
import garcia.ludovic.photos.feature.photo.details.PhotoDetailsRoute

internal const val photoIdArg = "photoId"
internal const val albumIdArg = "albumId"
internal const val titleArg = "title"
internal const val urlArg = "url"
internal const val thumbnailUrlArg = "thumbnailUrl"
internal const val photoDetailsNavigationRoute = "photo_details_route"
internal const val photoDetailsBottomSheetNavigationRoute = "photo_details_bottom_sheet_route"

fun NavController.navigateToPhotoDetails(photoId: Int) {
    // Prevent duplicated navigation
    val currentRoute = currentBackStackEntry?.destination?.route
    if (currentRoute?.startsWith(photoDetailsNavigationRoute) == false) {
        navigate("$photoDetailsNavigationRoute/$photoId")
    }
}

fun NavController.navigateToPhotoDetailsBottomSheet(
    photoId: Int,
    albumId: Int,
    title: String,
    url: String,
    thumbnailUrl: String
) = navigate("$photoDetailsBottomSheetNavigationRoute/$photoId/$albumId/$title/${Uri.encode(url)}/${Uri.encode(thumbnailUrl)}")

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.photoDetailsRoute(
    navController: NavController
) {
    animatedComposable(
        route = "$photoDetailsNavigationRoute/{$photoIdArg}",
        arguments = listOf(
            navArgument(photoIdArg) { type = NavType.IntType }
        )
    ) {
        PhotoDetailsRoute(
            onInfo = {
                // Prevent duplicated navigation
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                if (currentRoute?.startsWith(photoDetailsBottomSheetNavigationRoute) == false) {
                    navController.navigateToPhotoDetailsBottomSheet(it.id, it.albumId, it.title, it.url, it.thumbnailUrl)
                }
            }
        )
    }
    bottomSheet(
        route = "$photoDetailsBottomSheetNavigationRoute/{$photoIdArg}/{$albumIdArg}/{$titleArg}/{$urlArg}/{$thumbnailUrlArg}",
        arguments = listOf(
            navArgument(photoIdArg) { type = NavType.IntType },
            navArgument(albumIdArg) { type = NavType.IntType },
            navArgument(titleArg) { type = NavType.StringType },
            navArgument(urlArg) { type = NavType.StringType },
            navArgument(thumbnailUrlArg) { type = NavType.StringType }
        )
    ) { backstackEntry ->
        val id = backstackEntry.arguments?.getInt(photoIdArg) ?: throw Exception("Missing argument :(")
        val albumId = backstackEntry.arguments?.getInt(albumIdArg) ?: throw Exception("Missing argument :(")
        val title = backstackEntry.arguments?.getString(titleArg) ?: throw Exception("Missing argument :(")
        val url = backstackEntry.arguments?.getString(urlArg)?.run { Uri.decode(this) } ?: throw Exception("Missing argument :(")
        val thumbnailUrl = backstackEntry.arguments?.getString(thumbnailUrlArg)?.run { Uri.decode(this) } ?: throw Exception("Missing argument :(")
        PhotoDetailsBottomSheetRoute(id, albumId, title, url, thumbnailUrl)
    }
}
