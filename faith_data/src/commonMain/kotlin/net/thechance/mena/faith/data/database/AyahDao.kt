package net.thechance.mena.faith.data.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AyahDao {
    @Query("SELECT * FROM ayat WHERE sura_no = :surahNumber")
    suspend fun getAyatOfSurah(surahNumber: Int): List<AyahDto>

    @Query("""SELECT 
        sura_no,
        sura_name_en,
        sura_name_ar,
        COUNT(*) as ayahCount
    FROM ayat 
    GROUP BY sura_no, sura_name_en, sura_name_ar
    ORDER BY sura_no ASC
""")
    suspend fun getAllSur(): List<SurahDto>

    @Query("""
        SELECT aya_text 
        FROM ayat 
        WHERE sura_no = :surahId AND aya_no = :ayahNumber
        LIMIT 1
    """)
    suspend fun getAyahContent(ayahNumber:Int, surahId: Int): String
}