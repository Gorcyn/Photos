package garcia.ludovic.photos.benchmark.scenario

import androidx.test.uiautomator.UiDevice
import garcia.ludovic.photos.benchmark.steps.clickPhotoDetailsInfoIcon
import garcia.ludovic.photos.benchmark.steps.clickRandomPhoto
import garcia.ludovic.photos.benchmark.steps.idle
import garcia.ludovic.photos.benchmark.steps.scrollDownGallery
import garcia.ludovic.photos.benchmark.steps.scrollUpGallery
import garcia.ludovic.photos.benchmark.steps.zoomInPhoto
import garcia.ludovic.photos.benchmark.steps.zoomOutPhoto

internal fun UiDevice.browseAndZoom() {
    // Scroll through the gallery
    scrollDownGallery()
    scrollUpGallery()

    // Click on an item
    clickRandomPhoto()

    // Zoom in image then out
    zoomInPhoto()
    zoomOutPhoto()

    // Check details
    clickPhotoDetailsInfoIcon()

    // Close bottom sheet
    pressBack()
    idle()

    // Go back to gallery
    pressBack()
    idle()

    // Scroll down again
    scrollDownGallery()
}
