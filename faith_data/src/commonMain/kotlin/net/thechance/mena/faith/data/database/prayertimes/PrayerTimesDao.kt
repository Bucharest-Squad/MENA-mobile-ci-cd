package net.thechance.mena.faith.data.database.prayertimes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.datetime.LocalDate
import kotlin.time.ExperimentalTime

@Dao
interface PrayerTimesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPrayerTimes(prayerTimes: PrayerTimesLocal)

    @Query("SELECT * FROM prayer_times WHERE date = :date")
    suspend fun getPrayerTimesByDate(date: LocalDate): List<PrayerTimesLocal>

    @Query("SELECT * FROM prayer_times WHERE hijri_date = :hijriDate")
    suspend fun getPrayerTimesByHijri(hijriDate: String): List<PrayerTimesLocal>

    @OptIn(ExperimentalTime::class)
    @Query("DELETE FROM prayer_times WHERE saved_in < :expiredDate")
    suspend fun deleteExpiredPrayerTimes(expiredDate: LocalDate)
}
