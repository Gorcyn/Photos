package garcia.ludovic.photos.feature.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import garcia.ludovic.photos.core.data.PhotoRepository
import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.feature.gallery.model.DisplayStyle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            error.emit(exception)
        }
        viewModelScope.launch {
            delay(300L)
            isRefreshing.emit(false)
        }
    }

    private val photoList: Flow<List<Photo>> = photoRepository.getPhotoList()

    private val displayStyle = MutableStateFlow(DisplayStyle.Four)

    private val isRefreshing = MutableStateFlow<Boolean?>(null)

    private val error = MutableStateFlow<Throwable?>(null)

    val uiState: StateFlow<GalleryUiState> = combine(
        displayStyle,
        photoList,
        isRefreshing,
        error
    ) { displayStyle, photoList, isRefreshing, error ->
        GalleryUiState(displayStyle, photoList, isRefreshing, error)
    }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = GalleryUiState()
        )

    fun switchStyle() {
        viewModelScope.launch(exceptionHandler) {
            displayStyle.emit(displayStyle.value.next())
        }
    }

    fun reload() {
        viewModelScope.launch(exceptionHandler) {
            isRefreshing.emit(true)
            photoRepository.sync()
        }
        viewModelScope.launch(exceptionHandler) {
            delay(300L)
            isRefreshing.emit(false)
        }
    }

    fun onErrorConsumed() {
        viewModelScope.launch(exceptionHandler) {
            error.emit(null)
        }
    }
}
