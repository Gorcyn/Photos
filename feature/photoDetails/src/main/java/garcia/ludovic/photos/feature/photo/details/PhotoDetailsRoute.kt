package garcia.ludovic.photos.feature.photo.details

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.core.design.LocalSnackbarHostState
import garcia.ludovic.photos.feature.photo.details.ui.PhotoDetailsScreen

@Composable
fun PhotoDetailsRoute(
    viewModel: PhotoDetailsViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    snackbarHostState: SnackbarHostState = LocalSnackbarHostState.current,
    onInfo: (photo: Photo) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // On error
    uiState.error?.let {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(message = it.message ?: "")
            viewModel.onErrorConsumed()
        }
    }

    PhotoDetailsScreen(
        photoId = uiState.photoId,
        photo = uiState.photo,
        context = context,
        onInfo = onInfo
    )
}
