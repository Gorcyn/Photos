package garcia.ludovic.photos.core.design.animation

import androidx.compose.animation.AnimatedContentScope.SlideDirection
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.animatedComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = {
            slideIntoContainer(SlideDirection.Start, animationSpec = tween(AnimationDuration.DEFAULT))
        },
        exitTransition = {
            slideOutOfContainer(SlideDirection.Start, animationSpec = tween(AnimationDuration.DEFAULT))
        },
        popEnterTransition = {
            slideIntoContainer(SlideDirection.End, animationSpec = tween(AnimationDuration.DEFAULT))
        },
        popExitTransition = {
            slideOutOfContainer(SlideDirection.End, animationSpec = tween(AnimationDuration.DEFAULT))
        },
        content = content
    )
}
