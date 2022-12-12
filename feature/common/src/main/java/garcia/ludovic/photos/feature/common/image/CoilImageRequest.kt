package garcia.ludovic.photos.feature.common.image

import android.content.Context
import coil.request.ImageRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val PHOTO = "photo_"
private const val THUMBNAIL = "photo_thumbnail_"

class CoilImageRequest @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private fun ImageRequest.Builder.noCacheControl() =
        setHeader("Cache-Control", "no-cache")

    fun photo(photoId: Int, photoUrl: String?) =
        ImageRequest.Builder(context)
            .data(photoUrl)
            .noCacheControl()
            .diskCacheKey("$PHOTO$photoId")
            .placeholderMemoryCacheKey("$THUMBNAIL$photoId")
            .build()

    fun thumbnail(photoId: Int, thumbnailUrl: String?) =
        ImageRequest.Builder(context)
            .data(thumbnailUrl)
            .noCacheControl()
            .diskCacheKey("$THUMBNAIL$photoId")
            .memoryCacheKey("$THUMBNAIL$photoId")
            .build()
}
