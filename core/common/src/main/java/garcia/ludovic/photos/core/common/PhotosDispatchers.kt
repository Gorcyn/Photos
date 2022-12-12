package garcia.ludovic.photos.core.common

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val photosDispatchers: PhotosDispatchers)

enum class PhotosDispatchers {
    IO
}
