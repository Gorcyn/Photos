package garcia.ludovic.photos.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import garcia.ludovic.photos.core.data.DefaultPhotoRepository
import garcia.ludovic.photos.core.data.PhotoRepository
import garcia.ludovic.photos.core.data.util.DefaultNetworkMonitor
import garcia.ludovic.photos.core.data.util.NetworkMonitor

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsProjectRepository(
        projectRepository: DefaultPhotoRepository
    ): PhotoRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: DefaultNetworkMonitor
    ): NetworkMonitor
}
