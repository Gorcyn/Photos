package garcia.ludovic.photos.core.database.test

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import garcia.ludovic.photos.core.database.PhotosDatabase

class InMemoryPhotosDatabase : PhotosDatabase by PhotosDatabase(
    JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        PhotosDatabase.Schema.create(this)
    }
)
