package net.thechance.mena.faith.data.database

import android.content.Context
import androidx.room.Room

actual class QuranDatabaseBuilder(private val context: Context) {
    actual fun getBuilder() = Room.databaseBuilder<QuranDatabase>(
        context = context.applicationContext,
        name = "hafs_smart_v8.db"
    ).fallbackToDestructiveMigration(false)
        .createFromAsset(databaseUri.removePrefix("file:///android_asset/"))
}