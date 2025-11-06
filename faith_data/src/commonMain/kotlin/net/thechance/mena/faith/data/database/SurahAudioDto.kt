package net.thechance.mena.faith.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "surah_audio")
data class SurahAudioDto(
    @PrimaryKey val surahId: Int,
    @ColumnInfo(name = "reciter_id")
    val reciterId: Int,
    @ColumnInfo(name = "local_file_path")
    val localFilePath: String
)
