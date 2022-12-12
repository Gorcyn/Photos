package garcia.ludovic.photos.feature.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import garcia.ludovic.photos.feature.common.image.CoilImageRequest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureCommonModule {

    @Provides
    @Singleton
    fun providesCoilImageRequest(
        @ApplicationContext context: Context
    ): CoilImageRequest = CoilImageRequest(context)
}
