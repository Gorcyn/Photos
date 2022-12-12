package garcia.ludovic.photos.core.data.test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import garcia.ludovic.photos.core.data.PhotoRepository
import garcia.ludovic.photos.core.data.di.DataModule
import garcia.ludovic.photos.core.data.test.TestPhotoRepository
import garcia.ludovic.photos.core.data.test.util.TestNetworkMonitor
import garcia.ludovic.photos.core.data.util.NetworkMonitor

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface TestDataModule {

    @Binds
    fun bindsProjectRepository(
        projectRepository: TestPhotoRepository
    ): PhotoRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: TestNetworkMonitor
    ): NetworkMonitor
}
