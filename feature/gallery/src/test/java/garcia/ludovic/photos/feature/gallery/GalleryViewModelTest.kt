package garcia.ludovic.photos.feature.gallery

import garcia.ludovic.photos.core.data.test.TestPhotoRepository
import garcia.ludovic.photos.core.testing.util.DefaultDispatcherRule
import garcia.ludovic.photos.feature.gallery.model.DisplayStyle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryViewModelTest {

    @get:Rule
    val mainDispatcherRule = DefaultDispatcherRule()

    private val photoRepository = TestPhotoRepository()

    private lateinit var viewModel: GalleryViewModel

    @Before
    fun setup() {
        viewModel = GalleryViewModel(photoRepository)
    }

    @Test
    fun state_is_initially_default() = runTest {
        assertEquals(GalleryUiState(), viewModel.uiState.value)
    }

    @Test
    fun switches_style() = runTest {
        viewModel.switchStyle()
        assertEquals(DisplayStyle.Two, viewModel.uiState.first().displayStyle)
    }

    @Test
    fun reload() = runTest {
        viewModel.reload()
        assertTrue(viewModel.uiState.first().isRefreshing ?: false)
        delay(500L)
        assertFalse(viewModel.uiState.first().isRefreshing ?: true)
    }

    @Test
    fun onErrorConsumed() = runTest {
        photoRepository.willThrow = Exception("Network error.")
        viewModel.reload()
        assertTrue(viewModel.uiState.first().error is Exception)
        assertEquals("Network error.", viewModel.uiState.first().error?.message)

        viewModel.onErrorConsumed()
        assertNull(viewModel.uiState.first().error)
    }
}
