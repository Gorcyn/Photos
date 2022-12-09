package garcia.ludovic.photos.core.database

import kotlinx.coroutines.flow.Flow

interface PhotosLocalDataSource {

    fun selectAll(): Flow<List<LocalPhoto>>

    suspend fun select(id: Int): LocalPhoto?

    suspend fun save(localPhoto: LocalPhoto)

    suspend fun deleteAll()

    suspend fun delete(id: Int)

    suspend fun transaction(body: suspend PhotosLocalDataSource.() -> Unit) = body()
}
