package garcia.ludovic.photos.feature.gallery.model

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.grid.GridCells
import garcia.ludovic.photos.feature.gallery.R

enum class DisplayStyle(
    @DrawableRes val icon: Int,
    @StringRes val contentDescription: Int,
    private val gridCellsPortrait: Int,
    private val gridCellsLandscape: Int
) {
    Two(
        R.drawable.ic_baseline_grid_view_24,
        R.string.gallery_display_mode_grid_2,
        2,
        5
    ),
    Four(
        R.drawable.ic_baseline_grid_on_24,
        R.string.gallery_display_mode_grid_4,
        4,
        9
    );

    fun next(): DisplayStyle = when (this) {
        Two -> Four
        else -> Two
    }

    fun gridCellsFor(configuration: Configuration): GridCells = GridCells.Fixed(
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> gridCellsLandscape
            else -> gridCellsPortrait
        }
    )
}
