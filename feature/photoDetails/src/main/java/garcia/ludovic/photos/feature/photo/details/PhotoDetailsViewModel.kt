package garcia.ludovic.photos.feature.photo.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import garcia.ludovic.photos.core.data.PhotoRepository
import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.feature.photo.details.navigation.photoIdArg
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val photoId: Int = checkNotNull(savedStateHandle[photoIdArg])

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            error.emit(exception)
            isRefreshing.emit(false)
        }
    }

    private val photo = MutableStateFlow<Photo?>(null)

    private val isRefreshing = MutableStateFlow(false)

    private val error = MutableStateFlow<Throwable?>(null)

    val uiState: StateFlow<PhotoDetailsUiState> = combine(
        photo,
        isRefreshing,
        error
    ) { photo, isRefreshing, error ->
        PhotoDetailsUiState(photoId, photo, isRefreshing, error)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = PhotoDetailsUiState(photoId)
        )

    init {
        viewModelScope.launch(exceptionHandler) {
            isRefreshing.emit(true)
            photo.emit(photoRepository.getPhoto(photoId))
            delay(500L)
            isRefreshing.emit(false)
        }
    }

    fun onErrorConsumed() {
        viewModelScope.launch {
            error.emit(null)
        }
    }
}
