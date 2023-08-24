package garcia.ludovic.photos.core.database.test

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import garcia.ludovic.photos.core.database.LocalPhoto
import garcia.ludovic.photos.core.database.PhotosDatabase

class InMemoryPhotosDatabase : PhotosDatabase by PhotosDatabase(
    JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        PhotosDatabase.Schema.create(this)
    },
    LocalPhoto.Adapter(
        idAdapter = IntColumnAdapter,
        albumIdAdapter = IntColumnAdapter
    )
)
