package garcia.ludovic.photos.feature.photo.details.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import garcia.ludovic.photos.feature.common.image.CoilImageRequest
import garcia.ludovic.photos.feature.photo.details.R
import garcia.ludovic.photos.feature.photo.details.util.TransformGestures

@Composable
fun PhotoDetailsScreen(
    photoId: Int,
    photo: Photo?,
    context: Context = LocalContext.current,
    configuration: Configuration = LocalConfiguration.current,
    onInfo: (photo: Photo) -> Unit = {}
) {
    val imageRequest = CoilImageRequest(context)
    val painter = rememberAsyncImagePainter(
        model = imageRequest.photo(photoId, photo?.url)
    )

    var zoom by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(IntOffset(0, 0)) }

    val screenSize = Offset(configuration.screenWidthDp.dp.value, configuration.screenHeightDp.dp.value)

    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier
            .fillMaxSize()
            .background(PhotosTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .clip(RectangleShape)
                .fillMaxSize()
                .background(PhotosTheme.colorScheme.background)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, gestureZoom, _ ->
                        zoom = TransformGestures.coerceZoom(zoom, gestureZoom)
                        offset = TransformGestures.coerceOffset(
                            screenSize,
                            offset,
                            pan,
                            zoom
                        )
                    }
                }
                .testTag("PhotoDetailsImageBox")
        ) {
            Icon(
                painterResource(id = R.drawable.ic_outline_image_not_supported_24),
                contentDescription = stringResource(R.string.photo_details_photo_placeholder),
                tint = PhotosTheme.colorScheme.onBackground,
                modifier = Modifier
                    .offset { offset }
                    .align(Alignment.Center)
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .padding(64.dp)
                    .testTag("PhotoDetailsImagePlaceholder")
            )
            Image(
                modifier = Modifier
                    .offset { offset }
                    .align(Alignment.Center)
                    .graphicsLayer(scaleX = zoom, scaleY = zoom)
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .testTag("PhotoDetailsImage"),
                contentDescription = photo?.title,
                painter = painter
            )
        }
        photo?.let {
            Box(modifier = Modifier.padding(8.dp)) {
                IconButton(onClick = { onInfo(it) }) {
                    Icon(
                        painterResource(id = R.drawable.ic_outline_info_24),
                        contentDescription = stringResource(id = R.string.photo_details_info_description),
                        tint = PhotosTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.testTag("PhotoDetailsIcon")
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PhotoDetailsScreen_Preview_No_Photo() {
    PhotosTheme {
        PhotoDetailsScreen(
            1,
            null
        )
    }
}

@Composable
@Preview
fun PhotoDetailsScreen_Preview_Photo() {
    PhotosTheme {
        PhotoDetailsScreen(
            1,
            Photo(
                id = 1,
                albumId = 1,
                title = "Title 1",
                url = "https://via.placeholder.com/600/92c952",
                thumbnailUrl = "https://via.placeholder.com/150/92c952"
            )
        )
    }
}
