package garcia.ludovic.photos.core.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import garcia.ludovic.photos.core.network.BuildConfig
import garcia.ludovic.photos.core.network.DefaultPhotoNetworkDataSource
import garcia.ludovic.photos.core.network.PhotoNetworkDataSource
import garcia.ludovic.photos.core.network.retrofit.PhotoNetworkApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.HEADERS)
    }

    @Provides
    @Singleton
    fun providesPhotosNetworkApi(
        networkJson: Json,
        loggingInterceptor: HttpLoggingInterceptor
    ): PhotoNetworkApi = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        )
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(PhotoNetworkApi::class.java)

    @Provides
    @Singleton
    fun providesPhotosNetworkDataSource(
        photosNetworkApi: PhotoNetworkApi
    ): PhotoNetworkDataSource = DefaultPhotoNetworkDataSource(photosNetworkApi)
}
