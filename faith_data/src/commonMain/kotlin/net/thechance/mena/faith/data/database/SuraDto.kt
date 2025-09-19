package net.thechance.mena.faith.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "surah")
data class SuraDto(
    @PrimaryKey val id: Int,
    val name: String,
    val ayahCount: Int,
    val isMakkia: Boolean
)