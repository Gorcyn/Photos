package garcia.ludovic.photos.feature.photo.details.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import org.junit.Assert.assertEquals
import org.junit.Test

class TransformGesturesTest {

    @Test
    fun calculates_zoom() {
        val zoom = TransformGestures.coerceZoom(1f, 1.5f)
        assertEquals(1.5f, zoom)
    }

    @Test
    fun coerces_zoom_min() {
        val zoom = TransformGestures.coerceZoom(1f, -1f)
        assertEquals(ZOOM_MIN, zoom)
    }

    @Test
    fun coerces_zoom_max() {
        val zoom = TransformGestures.coerceZoom(1f, 5f)
        assertEquals(ZOOM_MAX, zoom)
    }

    @Test
    fun calculates_offset() {
        val screenSize = Offset(100f, 100f)
        val originalOffset = IntOffset(0, 0)
        val pan = Offset(12f, 12f)
        val offset = TransformGestures.coerceOffset(screenSize, originalOffset, pan, 4f)
        assertEquals(48, offset.x)
        assertEquals(48, offset.y)
    }

    @Test
    fun coerces_offset_min() {
        val screenSize = Offset(100f, 100f)
        val originalOffset = IntOffset(-200, -200)
        val pan = Offset(-50f, -50f)
        val offset = TransformGestures.coerceOffset(screenSize, originalOffset, pan, 4f)
        assertEquals(-400, offset.x)
        assertEquals(-400, offset.y)
    }

    @Test
    fun coerces_offset_max() {
        val screenSize = Offset(100f, 100f)
        val originalOffset = IntOffset(200, 200)
        val pan = Offset(50f, 50f)
        val offset = TransformGestures.coerceOffset(screenSize, originalOffset, pan, 4f)
        assertEquals(400, offset.x)
        assertEquals(400, offset.y)
    }
}
