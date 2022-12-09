package garcia.ludovic.photos.core.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomPhotoDao {

    @Query("SELECT * FROM localPhoto ORDER BY id")
    fun selectAll(): Flow<List<RoomPhoto>>

    @Query("SELECT * FROM localPhoto WHERE id = (:id)")
    suspend fun select(id: Int): RoomPhoto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(photo: RoomPhoto)

    @Query("DELETE FROM localPhoto WHERE id = (:id)")
    suspend fun delete(id: Int)

    @Query("DELETE FROM localPhoto")
    suspend fun deleteAll()
}
