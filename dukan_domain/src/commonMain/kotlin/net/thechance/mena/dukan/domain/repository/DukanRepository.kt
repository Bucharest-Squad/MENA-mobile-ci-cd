package net.thechance.mena.dukan.domain.repository

import net.thechance.mena.dukan.domain.entity.Category
import net.thechance.mena.dukan.domain.entity.Dukan
import net.thechance.mena.dukan.domain.entity.DukanColor
import net.thechance.mena.dukan.domain.entity.MyDukanStatus

interface DukanRepository {
    suspend fun createDukan(dukan: Dukan)
    suspend fun getDukanStyles(): List<Dukan.Style>
    suspend fun getCategories(): List<Category>
    suspend fun getDukanColors(): List<DukanColor>
    suspend fun isDukanNameTaken(name: String): Boolean
    suspend fun getMyDukanStatus(): MyDukanStatus
}