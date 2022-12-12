package garcia.ludovic.photos.core.data.util

interface NetworkMonitor {

    suspend fun isOnline(): Boolean
}
