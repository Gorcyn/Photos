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
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT_CONNECTION = 1L
private const val TIMEOUT_WRITE = 1L
private const val TIMEOUT_READ = 1L
private val TIMEOUT_UNIT = TimeUnit.SECONDS

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
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECTION, TIMEOUT_UNIT)
            .writeTimeout(TIMEOUT_WRITE, TIMEOUT_UNIT)
            .readTimeout(TIMEOUT_READ, TIMEOUT_UNIT)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesPhotosNetworkApi(
        networkJson: Json,
        okHttpClient: OkHttpClient
    ): PhotoNetworkApi = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(
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
