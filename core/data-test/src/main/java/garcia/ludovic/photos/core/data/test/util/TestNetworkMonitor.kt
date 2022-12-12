package garcia.ludovic.photos.core.data.test.util

import garcia.ludovic.photos.core.data.util.NetworkMonitor
import javax.inject.Inject

class TestNetworkMonitor @Inject constructor() : NetworkMonitor {

    var isOnline: Boolean = true

    override suspend fun isOnline(): Boolean = isOnline
}
