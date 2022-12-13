package garcia.ludovic.photos.core.design

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import garcia.ludovic.photos.core.design.theme.PhotosTheme

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialNavigationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PhotosApp(
    startDestination: String,
    builder: NavGraphBuilder.(navController: NavController) -> Unit
) {
    PhotosTheme {
        val backgroundColor = PhotosTheme.colorScheme.background
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setStatusBarColor(backgroundColor, useDarkIcons)
            systemUiController.setNavigationBarColor(backgroundColor, useDarkIcons)
            onDispose {}
        }

        val bottomSheetNavigator = rememberBottomSheetNavigator()
        val navController = rememberAnimatedNavController(bottomSheetNavigator)
        val snackbarHostState = remember { SnackbarHostState() }

        CompositionLocalProvider(
            LocalSnackbarHostState provides snackbarHostState
        ) {
            Scaffold(
                containerColor = PhotosTheme.colorScheme.background,
                contentColor = PhotosTheme.colorScheme.onBackground,
                snackbarHost = {
                    SnackbarHost(snackbarHostState)
                },
                modifier = Modifier
                    .testTag("Scaffold")
                    .semantics {
                        testTagsAsResourceId = true
                    }
            ) { paddingValues ->
                ModalBottomSheetLayout(
                    bottomSheetNavigator,
                    modifier = Modifier
                        .background(PhotosTheme.colorScheme.background)
                        .padding(paddingValues)
                ) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = startDestination,
                        builder = {
                            builder(navController)
                        },
                        modifier = Modifier
                            .background(PhotosTheme.colorScheme.background)
                    )
                }
            }
        }
    }
}
