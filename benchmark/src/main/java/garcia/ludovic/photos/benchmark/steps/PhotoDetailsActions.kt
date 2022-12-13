package garcia.ludovic.photos.benchmark.steps

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice

private const val PHOTO_DETAILS_IMAGE_BOX_TAG = "PhotoDetailsImageBox"
private const val PHOTO_DETAILS_ICON = "PhotoDetailsIcon"

internal fun UiDevice.zoomInPhoto(percent: Float = 0.5f) = findObject(By.res(PHOTO_DETAILS_IMAGE_BOX_TAG)).also {
    it.pinchOpen(percent)
    idle()
}

internal fun UiDevice.zoomOutPhoto(percent: Float = 0.5f) = findObject(By.res(PHOTO_DETAILS_IMAGE_BOX_TAG)).also {
    it.pinchClose(percent)
    idle()
}

internal fun UiDevice.clickPhotoDetailsInfoIcon() = findObject(By.res(PHOTO_DETAILS_ICON)).also {
    it.click()
    idle()
}
