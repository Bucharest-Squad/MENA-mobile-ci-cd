package net.thechance.mena.faith.data.database

import androidx.room.RoomDatabase
import net.thechance.mena.faith.data.utils.DataDispatchers

fun getQuranDatabase(
    builder: RoomDatabase.Builder<QuranDatabase>
): QuranDatabase {
    return builder
        .setQueryCoroutineContext(DataDispatchers().io)
        .build()
}