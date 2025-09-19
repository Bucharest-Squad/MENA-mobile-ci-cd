package net.thechance.mena.faith.data.database

import androidx.room.ColumnInfo

data class SurahDbModel(
    @ColumnInfo(name = "sura_no") val order: Int,
    @ColumnInfo(name = "sura_name_en") val nameEn: String,
    @ColumnInfo(name = "sura_name_ar") val nameAr: String,
    val ayahCount: Int
)
