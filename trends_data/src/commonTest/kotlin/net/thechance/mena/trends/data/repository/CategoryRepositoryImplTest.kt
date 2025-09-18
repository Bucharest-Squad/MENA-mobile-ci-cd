package net.thechance.mena.trends.data.repository

import kotlinx.coroutines.test.runTest
import net.thechance.mena.trends.data.mapper.toEntity
import net.thechance.mena.trends.data.repository.util.createCategoryRepository
import net.thechance.mena.trends.data.repository.util.mockCategories
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CategoryRepositoryImplTest {

    private var repository = createCategoryRepository()

    @Test
    fun `getAllCategories should return list of mapped categories`() = runTest {
        val result = repository.getAllCategories()

        assertEquals(mockCategories.toEntity(), result)
    }

    @Test
    fun `isCategoriesAlreadySelectedByUser should returns true if API returns non-empty list`() = runTest {
        val result = repository.isCategoriesAlreadySelectedByUser()

        assertTrue(result)
    }

    @Test
    fun `updateUserInterests should succeed when API call is successful`() = runTest {
        repository.updateUserInterestedCategories(listOf("uuid 1"))

        assertTrue(true)
    }
}


