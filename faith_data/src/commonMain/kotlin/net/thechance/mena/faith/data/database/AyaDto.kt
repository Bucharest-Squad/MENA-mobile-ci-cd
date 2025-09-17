package net.thechance.mena.faith.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ayat")
data class AyaDto(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "sura_no") val suraNo: Int,
    @ColumnInfo(name = "sura_name_en") val suraNameEn: String,
    @ColumnInfo(name = "aya_no") val ayaNo: Int,
    @ColumnInfo(name = "aya_text") val ayaText: String,
)