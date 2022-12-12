package garcia.ludovic.photos.feature.gallery.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import garcia.ludovic.photos.core.data.model.Photo
import garcia.ludovic.photos.core.design.theme.PhotosTheme
import garcia.ludovic.photos.core.design.ui.PhotosTopAppBar
import garcia.ludovic.photos.core.design.ui.modifier.verticalScrollbar
import garcia.ludovic.photos.feature.gallery.R
import garcia.ludovic.photos.feature.gallery.model.DisplayStyle

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun GalleryScreen(
    pullRefreshState: PullRefreshState = rememberPullRefreshState(false, {}),
    lazyGridState: LazyGridState = rememberLazyGridState(),
    context: Context = LocalContext.current,
    configuration: Configuration = LocalConfiguration.current,
    displayStyle: DisplayStyle = DisplayStyle.Four,
    isRefreshing: Boolean,
    photoList: List<Photo>,
    onDisplayStyle: () -> Unit = {},
    onClick: (photoId: Int) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PhotosTheme.colorScheme.background)
    ) {
        PhotosTopAppBar(
            title = stringResource(id = R.string.gallery_title),
            scrollBehavior
        ) {
            if (photoList.isNotEmpty()) {
                IconButton(onClick = onDisplayStyle) {
                    Icon(
                        painter = painterResource(id = displayStyle.next().icon),
                        contentDescription = stringResource(displayStyle.next().contentDescription),
                        tint = PhotosTheme.colorScheme.onBackground
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .pullRefresh(pullRefreshState)
                .clipToBounds()
        ) {
            LazyVerticalGrid(
                state = lazyGridState,
                columns = displayStyle.gridCellsFor(configuration),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .fillMaxSize()
                    .verticalScrollbar(
                        lazyGridState,
                        color = PhotosTheme.colorScheme.onBackground
                    )
                    .testTag("GalleryGrid")
            ) {
                items(items = photoList, key = { it.id }) { photo ->
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data(photo.thumbnailUrl)
                            .diskCacheKey("photo_thumbnail_${photo.id}")
                            .memoryCacheKey("photo_thumbnail_${photo.id}")
                            .build()
                    )
                    PhotoItem(
                        painter = painter,
                        title = photo.title,
                        Modifier.animateItemPlacement()
                            .testTag("GalleryPhotoItem_${displayStyle.name}")
                    ) {
                        onClick(photo.id)
                    }
                }
            }

            PullRefreshIndicator(
                isRefreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
                    .testTag("GalleryPullRefreshIndicator")
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun GalleryScreen_Preview_Grid() {
    PhotosTheme {
        GalleryScreen(
            displayStyle = DisplayStyle.Four,
            isRefreshing = false,
            photoList = (1..36).map {
                Photo(it, 1, "Photo $it", "url $it", "thumbnailUrl $it")
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun GalleryScreen_Preview_List() {
    PhotosTheme {
        GalleryScreen(
            displayStyle = DisplayStyle.Two,
            isRefreshing = false,
            photoList = (1..10).map {
                Photo(it, 1, "Photo $it", "url $it", "thumbnailUrl $it")
            }
        )
    }
}
