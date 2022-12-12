package garcia.ludovic.photos.core.design.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import garcia.ludovic.photos.core.design.theme.PhotosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(text = title) },
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = PhotosTheme.colorScheme.background,
            scrolledContainerColor = PhotosTheme.colorScheme.background,
            navigationIconContentColor = PhotosTheme.colorScheme.onBackground,
            titleContentColor = PhotosTheme.colorScheme.onBackground,
            actionIconContentColor = PhotosTheme.colorScheme.onBackground
        ),
        modifier = Modifier.testTag("PhotosTopAppBar")
    )
}
