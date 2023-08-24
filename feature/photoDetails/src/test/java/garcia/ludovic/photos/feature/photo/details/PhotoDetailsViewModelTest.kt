package garcia.ludovic.photos.feature.photo.details

import androidx.lifecycle.SavedStateHandle
import garcia.ludovic.photos.core.data.test.TestPhotoRepository
import garcia.ludovic.photos.core.testing.util.DefaultDispatcherRule
import garcia.ludovic.photos.feature.photo.details.navigation.photoIdArg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotoDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = DefaultDispatcherRule()

    private val photoRepository = TestPhotoRepository()
    private val savedStateHandle = SavedStateHandle(mapOf(photoIdArg to 1))

    private lateinit var viewModel: PhotoDetailsViewModel

    @Before
    fun setup() {
        viewModel = PhotoDetailsViewModel(savedStateHandle, photoRepository)
    }

    @Test
    fun state_is_initially_default() = runTest {
        assertEquals(PhotoDetailsUiState(1), viewModel.uiState.value)
    }

    @Test
    fun init_loads_photo() = runTest {
        assertTrue(viewModel.uiState.first().isRefreshing)
        delay(500L)
        assertFalse(viewModel.uiState.first().isRefreshing)
    }
}
