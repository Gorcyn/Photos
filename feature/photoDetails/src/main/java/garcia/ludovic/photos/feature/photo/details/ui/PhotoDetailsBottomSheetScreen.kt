package garcia.ludovic.photos.feature.photo.details.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import garcia.ludovic.photos.feature.photo.details.R

// Height to prevent crash on screen orientation change
// @see https://github.com/google/accompanist/issues/910#issuecomment-1278505156
fun Modifier.workaroundAccompanist910() = fillMaxHeight(0.51f)

@Composable
fun PhotoDetailsBottomSheet(
    id: Int,
    albumId: Int,
    title: String,
    url: String,
    thumbnailUrl: String
) {
    val configuration = LocalConfiguration.current

    val containerColor = PhotosTheme.colorScheme.background
    val contentColor = PhotosTheme.colorScheme.onBackground
    val secondaryContentColor = PhotosTheme.colorScheme.onBackground.copy(alpha = 0.75f)
    val titleStyle = PhotosTheme.typography.headlineMedium
    val contentStyle = PhotosTheme.typography.bodyMedium
    val secondaryContentStyle = PhotosTheme.typography.labelMedium

    val columns = when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> 1
        else -> 2
    }
    val detailsMap = mapOf(
        R.string.photo_details_photo_url to url,
        R.string.photo_details_photo_thumbnail_url to thumbnailUrl,
        R.string.photo_details_photo_id to "$id",
        R.string.photo_details_photo_album_id to "$albumId"
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .workaroundAccompanist910()
            .fillMaxWidth()
            .background(containerColor)
            .padding(16.dp)
    ) {
        item(
            span = { GridItemSpan(columns) }
        ) {
            Text(
                text = title,
                style = titleStyle,
                color = contentColor,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .testTag("PhotoDetailsBottomSheetTitle")
            )
        }
        items(detailsMap.entries.toList(), key = { it.key }) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(it.key),
                    style = secondaryContentStyle,
                    fontStyle = FontStyle.Italic,
                    color = secondaryContentColor,
                    modifier = Modifier
                        .testTag("PhotoDetailsBottomSheetSubTitle")
                )
                Text(
                    text = it.value,
                    style = contentStyle,
                    color = contentColor,
                    modifier = Modifier
                        .testTag("PhotoDetailsBottomSheetValue")
                )
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun PhotoDetailsBottomSheet_Preview() {
    PhotosTheme {
        PhotoDetailsBottomSheet(
            id = 1,
            albumId = 20,
            title = "assumenda voluptatem laboriosam enim consequatur veniam placeat reiciendis error",
            url = "https://via.placeholder.com/600/8985dc",
            thumbnailUrl = "https://via.placeholder.com/150/8985dc"
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 720,
    heightDp = 360
)
fun PhotoDetailsBottomSheet_Preview_Landscape() {
    PhotosTheme {
        PhotoDetailsBottomSheet(
            id = 1,
            albumId = 20,
            title = "assumenda voluptatem laboriosam enim consequatur veniam placeat reiciendis error",
            url = "https://via.placeholder.com/600/8985dc",
            thumbnailUrl = "https://via.placeholder.com/150/8985dc"
        )
    }
}
