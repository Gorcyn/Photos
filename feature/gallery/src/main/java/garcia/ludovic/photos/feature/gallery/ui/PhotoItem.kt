package garcia.ludovic.photos.feature.gallery.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import garcia.ludovic.photos.core.design.theme.PhotosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoItem(
    painter: Painter,
    title: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = { onClick() },
        shape = shape,
        modifier = modifier.fillMaxHeight(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 16.dp
        )
    ) {
        Image(
            painter = painter,
            contentDescription = title,
            modifier = Modifier
                .background(PhotosTheme.colorScheme.onSurfaceVariant)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(shape)
                .testTag("PhotoImage")
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun PhotoItem_Preview() {
    PhotosTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.background(PhotosTheme.colorScheme.background)
        ) {
            item {
                PhotoItem(
                    painter = ColorPainter(Color.LightGray),
                    title = "qui eius qui autem sed",
                    Modifier
                        .height(intrinsicSize = IntrinsicSize.Min)
                        .animateItemPlacement()
                ) {}
            }
            item {
                PhotoItem(
                    painter = ColorPainter(Color.DarkGray),
                    title = "assumenda voluptatem laboriosam enim consequatur veniam placeat reiciendis error",
                    Modifier
                        .height(intrinsicSize = IntrinsicSize.Min)
                        .animateItemPlacement()
                ) {}
            }
        }
    }
}
