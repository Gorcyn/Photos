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
    fun select(id: Int): RoomPhoto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(photo: RoomPhoto)

    @Query("DELETE FROM localPhoto WHERE id = (:id)")
    fun delete(id: Int)

    @Query("DELETE FROM localPhoto")
    fun deleteAll()
}
