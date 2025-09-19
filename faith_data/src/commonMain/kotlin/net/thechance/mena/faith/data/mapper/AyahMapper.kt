package net.thechance.mena.faith.data.mapper

import net.thechance.mena.faith.data.database.AyahDto
import net.thechance.mena.faith.data.database.SurahDto
import net.thechance.mena.faith.domain.entity.Ayah
import net.thechance.mena.faith.domain.entity.Surah

fun AyahDto.toAyah(): Ayah {
    return Ayah(
        number = this.number,
        surahId = this.id ?: 1,
        content = text
    )
}

fun SurahDto.toSurah(): Surah {
    val surahOrder = Surah.SurahOrder.entries.first { it.order == order }
    return Surah(
        id = order,
        order = surahOrder,
        name = nameEn,
        ayahCount = ayahCount,
        isMakkia = surahOrder.isMakkia
    )
}
