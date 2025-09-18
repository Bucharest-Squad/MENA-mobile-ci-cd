package net.thechance.mena.trends.data.mapper

import net.thechance.mena.trends.data.dto.CategoryDto
import kotlin.test.Test
import kotlin.test.assertEquals

class CategoryMapperTest {

    @Test
    fun `categoryDto toEntity() should map correctly`() {
        val dto = CategoryDto(id = "uuid 1", name = "Sport", emoji = "⚽")
        val category = dto.toEntity()

        assertEquals("uuid 1", category.id)
        assertEquals("Sport", category.name)
        assertEquals("⚽", category.emoji)
    }

    @Test
    fun `categoryDto with null values toEntity() should map to default values`() {
        val dto = CategoryDto(id = null, name =  null, emoji =  null)
        val category = dto.toEntity()

        assertEquals("", category.id)
        assertEquals("", category.name)
        assertEquals("", category.emoji)
    }

    @Test
    fun `List of categoryDto toEntity() should map to List of Category correctly`() {
        val dtos = listOf(
            CategoryDto(id = "uuid 1", name = "Sport", emoji = "⚽"),
            CategoryDto(id = "uuid 2", name = "Music", emoji = "🎵")
        )

        val categories = dtos.toEntity()

        assertEquals("uuid 1", categories[0].id)
        assertEquals("Sport", categories[0].name)
        assertEquals("⚽", categories[0].emoji)
        assertEquals("uuid 2", categories[1].id)
        assertEquals("Music", categories[1].name)
        assertEquals("🎵", categories[1].emoji)
        assertEquals(categories.size, 2)
    }
}