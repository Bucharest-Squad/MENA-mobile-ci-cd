package net.thechance.mena.faith.data.repository

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import net.thechance.mena.faith.data.database.prayertimes.PrayerTimesDao
import net.thechance.mena.faith.data.database.prayertimes.toDomain
import net.thechance.mena.faith.data.database.prayertimes.toLocal
import net.thechance.mena.faith.data.mapper.prayertime.toDomain
import net.thechance.mena.faith.data.remote.model.prayertime.PrayerTimesDto
import net.thechance.mena.faith.data.remote.service.PrayerTimeApiService
import net.thechance.mena.faith.data.utils.executeApiSafely
import net.thechance.mena.faith.data.utils.executeLocalSafely
import net.thechance.mena.faith.data.utils.loadFromCacheOrFetch
import net.thechance.mena.faith.domain.entity.PrayerTime
import net.thechance.mena.faith.domain.repository.PrayerTimeRepository
import net.thechance.mena.identity.domain.entity.Address
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class PrayerTimeRepositoryImpl(
    private val prayerTimeApiService: PrayerTimeApiService,
    private val prayerTimesDao: PrayerTimesDao
) : PrayerTimeRepository {

    init {
//        val scope = CoroutineScope(Dispatchers.IO)
//        scope.launch {
//
//            prayerTimesDao.deleteExpiredPrayerTimes(
//                expiredDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(
//                    5, DateTimeUnit.DAY
//                )
//            )
//        }
    }

    override suspend fun getPrayerTimes(
        date: Instant,
        address: Address?,
        timeZone: TimeZone,
    ): List<PrayerTime> {
        return loadFromCacheOrFetch(
            cacheBlock = {
                executeLocalSafely {
                    prayerTimesDao.getPrayerTimes(
                        date = LocalDate.parse(date.toDateString(timeZone = timeZone))
                    )
                }
            },
            networkBlock = {
                address?.let {
                    executeApiSafely {
                        prayerTimeApiService.getPrayerTimes(
                            date = date.toDateString(timeZone = timeZone),
                            latitude = address.latitude,
                            longitude = address.longitude
                        )
                    }.toLocal(
                        date = LocalDate.parse(date.toDateString(timeZone = timeZone)),
                        latitude = address.latitude,
                        longitude = address.longitude
                    )
                }
            },
            syncBlock = { it?.let { prayerTimesDao.insertPrayerTimes(it) } }
        )?.toDomain() ?: emptyList()
    }

    override suspend fun getPrayerTimeWithHijriDate(
        date: String,
        location: Address,
        timeZone: TimeZone,
        isHijri: Boolean
    ): List<PrayerTime> = executeApiSafely<PrayerTimesDto> {
        prayerTimeApiService.getPrayerTimes(
            date = date,
            latitude = location.latitude,
            longitude = location.longitude,
            isHijri = isHijri
        )
    }.toDomain()


    private fun Instant.toDateString(timeZone: TimeZone): String =
        this.toLocalDateTime(timeZone = timeZone).date.format(
            format = LocalDate.Formats.ISO
        )

}
