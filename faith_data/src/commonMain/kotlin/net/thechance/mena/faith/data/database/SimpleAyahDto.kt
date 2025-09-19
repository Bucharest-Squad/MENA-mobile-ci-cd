package net.thechance.mena.faith.data.database

import androidx.room.ColumnInfo

data class SimpleAyahDto(
val id: Int?,
@ColumnInfo(name = "aya_no") val number: Int?,
@ColumnInfo(name = "aya_text") val text: String?,
    @ColumnInfo(name = "aya_text_emlaey") val ayaTextEmlaey: String?,
)

