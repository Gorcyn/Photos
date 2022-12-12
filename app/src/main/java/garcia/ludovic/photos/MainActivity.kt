package garcia.ludovic.photos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import garcia.ludovic.photos.core.design.PhotosApp
import garcia.ludovic.photos.core.design.animation.animatedComposable
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import garcia.ludovic.photos.feature.gallery.navigation.galleryNavigationRoute
import garcia.ludovic.photos.feature.gallery.navigation.galleryRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PhotosApp(startDestination = galleryNavigationRoute) { navController ->
                galleryRoute {
                    navController.navigate("SecondScreen")
                }
                animatedComposable("SecondScreen") {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .clickable { navController.popBackStack() }
                    ) {
                        Text("SecondScreen content", color = PhotosTheme.colorScheme.onBackground)
                    }
                }
            }
        }
    }
}
