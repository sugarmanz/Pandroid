package com.jeremiahzucker.pandroid.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

actual class DatabaseDriverFactory {

    actual fun createDriver(): SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        .also(AppDatabase.Schema::create)

}
