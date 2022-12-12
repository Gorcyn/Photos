package garcia.ludovic.photos.feature.gallery.model

import android.content.res.Configuration
import androidx.compose.foundation.lazy.grid.GridCells
import org.junit.Assert.assertEquals
import org.junit.Test

class DisplayStyleTest {

    @Test
    fun four_next_gives_two() {
        assertEquals(DisplayStyle.Two, DisplayStyle.Four.next())
    }

    @Test
    fun four_gridCellsFor_portrait() {
        val configuration = Configuration().apply { orientation = Configuration.ORIENTATION_PORTRAIT }
        assertEquals(GridCells.Fixed(4), DisplayStyle.Four.gridCellsFor(configuration))
    }

    @Test
    fun four_gridCellsFor_landscape() {
        val configuration = Configuration().apply { orientation = Configuration.ORIENTATION_LANDSCAPE }
        assertEquals(GridCells.Fixed(9), DisplayStyle.Four.gridCellsFor(configuration))
    }

    @Test
    fun two_next_gives_four() {
        assertEquals(DisplayStyle.Four, DisplayStyle.Two.next())
    }

    @Test
    fun two_gridCellsFor_portrait() {
        val configuration = Configuration().apply { orientation = Configuration.ORIENTATION_PORTRAIT }
        assertEquals(GridCells.Fixed(2), DisplayStyle.Two.gridCellsFor(configuration))
    }

    @Test
    fun two_gridCellsFor_landscape() {
        val configuration = Configuration().apply { orientation = Configuration.ORIENTATION_LANDSCAPE }
        assertEquals(GridCells.Fixed(5), DisplayStyle.Two.gridCellsFor(configuration))
    }
}
