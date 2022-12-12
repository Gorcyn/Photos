package garcia.ludovic.photos.feature.photo.details.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

internal const val ZOOM_MIN = 1f
internal const val ZOOM_MAX = 4f

object TransformGestures {

    fun coerceZoom(zoom: Float, gestureZoom: Float) =
        (zoom * gestureZoom).coerceIn(ZOOM_MIN..ZOOM_MAX)

    fun coerceOffset(screenSize: Offset, offset: IntOffset, pan: Offset, zoom: Float): IntOffset {
        // No offset on original size
        if (zoom <= 1) {
            return IntOffset(0, 0)
        }
        val offsetX = (offset.x + (pan.x * zoom)).coerceIn(
            -(screenSize.x * zoom)..(screenSize.x * zoom)
        )
        val offsetY = (offset.y + (pan.y * zoom)).coerceIn(
            -(screenSize.y * zoom)..(screenSize.y * zoom)
        )
        return IntOffset(offsetX.toInt(), offsetY.toInt())
    }
}
