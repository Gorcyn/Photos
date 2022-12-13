package garcia.ludovic.photos.benchmark.steps

import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice

private const val GALLERY_GRID_TAG = "GalleryGrid"
private const val GALLERY_PHOTO_IMAGE_TAG = "PhotoImage"

internal fun UiDevice.scrollDownGallery() = findObject(By.res(GALLERY_GRID_TAG)).also {
    it.fling(Direction.DOWN)
    idle()
}

internal fun UiDevice.scrollUpGallery() = findObject(By.res(GALLERY_GRID_TAG)).also {
    it.fling(Direction.UP)
    idle()
}

internal fun UiDevice.clickRandomPhoto() = findObject(By.res(GALLERY_PHOTO_IMAGE_TAG)).also {
    it.click()
    idle()
}
