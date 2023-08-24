package garcia.ludovic.photos.core.data.test.util

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TestNetworkMonitorTest {

    private val testNetworkMonitor = TestNetworkMonitor()

    @Test
    fun isOnline_returns_true() = runTest {
        assertTrue(testNetworkMonitor.isOnline())
    }

    @Test
    fun isOnline_returns_false_when_asked() = runTest {
        testNetworkMonitor.isOnline = false

        assertFalse(testNetworkMonitor.isOnline())
    }
}
