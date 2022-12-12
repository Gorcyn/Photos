package garcia.ludovic.photos.feature.gallery

import android.content.Context
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import garcia.ludovic.photos.core.data.exception.OfflineException
import garcia.ludovic.photos.core.design.LocalSnackbarHostState
import garcia.ludovic.photos.feature.gallery.ui.GalleryScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GalleryRoute(
    viewModel: GalleryViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current,
    onClick: (photoId: Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Trigger refresh on start
    LaunchedEffect(key1 = uiState.isRefreshing) {
        if (uiState.isRefreshing == null) {
            viewModel.reload()
        }
    }

    // On error
    uiState.error?.let {
        val message = stringResource(
            when (it) {
                is OfflineException -> R.string.sync_not_available
                else -> R.string.sync_unexpected_error
            }
        )
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(message)
            viewModel.onErrorConsumed()
        }
    }

    // States
    val isRefreshing = uiState.isRefreshing ?: false
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
        viewModel.reload()
    })
    val lazyGridState = rememberLazyGridState()

    GalleryScreen(
        pullRefreshState = pullRefreshState,
        lazyGridState = lazyGridState,
        context = context,
        displayStyle = uiState.displayStyle,
        isRefreshing = isRefreshing,
        photoList = uiState.photoList,
        onDisplayStyle = { viewModel.switchStyle() },
        onClick = onClick
    )
}
