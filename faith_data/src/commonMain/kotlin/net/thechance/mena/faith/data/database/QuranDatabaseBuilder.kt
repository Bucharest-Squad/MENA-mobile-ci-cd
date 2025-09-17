package net.thechance.mena.faith.data.database

import androidx.room.RoomDatabase
import net.thechance.mena.faith_data.Res

expect class QuranDatabaseBuilder {
    fun getBuilder(): RoomDatabase.Builder<QuranDatabase>
}

internal val databaseUri = Res.getUri("files/hafs_smart_v8.db")