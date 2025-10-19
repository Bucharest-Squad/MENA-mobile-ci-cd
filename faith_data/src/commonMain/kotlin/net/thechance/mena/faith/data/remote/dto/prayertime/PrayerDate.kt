package net.thechance.mena.faith.data.remote.dto.prayertime

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrayerDate(
    @SerialName("hijri")
    val hijri: HijriDate?,
    @SerialName("gregorian")
    val gregorian: GregorianDate?
)
