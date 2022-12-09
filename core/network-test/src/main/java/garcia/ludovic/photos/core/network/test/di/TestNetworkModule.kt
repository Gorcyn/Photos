package garcia.ludovic.photos.core.network.test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import garcia.ludovic.photos.core.network.PhotoNetworkDataSource
import garcia.ludovic.photos.core.network.di.NetworkModule
import garcia.ludovic.photos.core.network.retrofit.PhotoNetworkApi
import garcia.ludovic.photos.core.network.test.TestPhotoNetworkDataSource
import garcia.ludovic.photos.core.network.test.TestPhotoNewsApi
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun providesPhotosNetworkApi(): PhotoNetworkApi =
        TestPhotoNewsApi()

    @Provides
    @Singleton
    fun providesPhotoNetworkDataSource(): PhotoNetworkDataSource =
        TestPhotoNetworkDataSource()
}
