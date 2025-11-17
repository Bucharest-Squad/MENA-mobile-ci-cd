package net.thechance.mena.faith.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import net.thechance.mena.faith.data.database.prayertimes.PrayerTimesDao
import net.thechance.mena.faith.data.database.prayertimes.PrayerTimesLocal
import net.thechance.mena.faith.data.database.prayertimes.toDomain
import net.thechance.mena.faith.data.database.prayertimes.toLocal
import net.thechance.mena.faith.data.mapper.prayertime.toDomain
import net.thechance.mena.faith.data.remote.service.PrayerTimeApiService
import net.thechance.mena.faith.data.utils.executeApiSafely
import net.thechance.mena.faith.data.utils.executeLocalSafely
import net.thechance.mena.faith.data.utils.loadFromCacheOrFetch
import net.thechance.mena.faith.domain.entity.PrayerTime
import net.thechance.mena.faith.domain.exception.FaithException
import net.thechance.mena.faith.domain.repository.PrayerTimeRepository
import net.thechance.mena.identity.domain.entity.Address
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class PrayerTimeRepositoryImpl(
    private val prayerTimeApiService: PrayerTimeApiService,
    private val prayerTimesDao: PrayerTimesDao
) : PrayerTimeRepository {

    init {
        cleanupExpiredData()
    }

    override suspend fun getPrayerTimes(
        date: Instant,
        address: Address?,
        timeZone: TimeZone,
    ): List<PrayerTime> {
        return loadFromCacheOrFetch(
            cacheBlock = {
                getLocalPrayerTimesByDate(LocalDate.parse(date.toDateString(timeZone = timeZone)))
            },
            networkBlock = {
                address?.let {
                    getRemotePrayerTimesByDateAndMapToLocal(
                        date = date,
                        address = it,
                        timeZone = timeZone
                    )
                }
            },
            syncBlock = { it?.let { prayerTimesDao.insertPrayerTimes(it) } }
        )?.toDomain() ?: throw FaithException.NetworkException
    }

    override suspend fun getPrayerTimeWithHijriDate(
        date: String,
        location: Address,
        timeZone: TimeZone,
        isHijri: Boolean
    ): List<PrayerTime> {
        return loadFromCacheOrFetch(
            cacheBlock = {
                executeLocalSafely {
                    prayerTimesDao.getPrayerTimesByHijri(hijriDate = date).toDomain()
                }
            },
            networkBlock = {
                executeApiSafely {
                    prayerTimeApiService.getPrayerTimes(
                        date = date,
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                }.toDomain()
            },
        )
    }

    private suspend fun getLocalPrayerTimesByDate(
        date: LocalDate
    ) = executeLocalSafely { prayerTimesDao.getPrayerTimesByDate(date = date) }

    private suspend fun getRemotePrayerTimesByDateAndMapToLocal(
        date: Instant,
        address: Address,
        timeZone: TimeZone
    ): PrayerTimesLocal {
        val stringDate = date.toDateString(timeZone = timeZone)
        return executeApiSafely {
            prayerTimeApiService.getPrayerTimes(
                date = stringDate,
                latitude = address.latitude,
                longitude = address.longitude
            )
        }.toLocal(
            date = LocalDate.parse(stringDate),
            latitude = address.latitude,
            longitude = address.longitude
        )
    }

    private fun cleanupExpiredData(days: Int = EXPIRED_DAYS) {
        CoroutineScope(Dispatchers.IO).launch {
            prayerTimesDao.deleteExpiredPrayerTimes(
                expiredDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(
                    days, DateTimeUnit.DAY
                )
            )
        }
    }

    private fun Instant.toDateString(timeZone: TimeZone): String =
        this.toLocalDateTime(timeZone = timeZone).date.format(
            format = LocalDate.Formats.ISO
        )

    companion object {
        private const val EXPIRED_DAYS = 1
    }

}
