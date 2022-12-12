package garcia.ludovic.photos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import garcia.ludovic.photos.core.design.PhotosApp
import garcia.ludovic.photos.feature.gallery.navigation.galleryNavigationRoute
import garcia.ludovic.photos.feature.gallery.navigation.galleryRoute
import garcia.ludovic.photos.feature.photo.details.navigation.navigateToPhotoDetails
import garcia.ludovic.photos.feature.photo.details.navigation.photoDetailsRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PhotosApp(startDestination = galleryNavigationRoute) { navController ->
                galleryRoute {
                    navController.navigateToPhotoDetails(it)
                }
                photoDetailsRoute(navController = navController)
            }
        }
    }
}
