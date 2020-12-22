package com.jeremiahzucker.pandroid.cache

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {

    fun createDriver(): SqlDriver

}
