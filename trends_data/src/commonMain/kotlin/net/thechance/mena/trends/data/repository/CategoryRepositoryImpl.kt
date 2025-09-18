package net.thechance.mena.trends.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import net.thechance.mena.trends.data.dto.CategoryDto
import net.thechance.mena.trends.data.mapper.toEntity
import net.thechance.mena.trends.data.util.safeApiCall
import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.domain.repository.CategoryRepository
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [CategoryRepository::class])
class CategoryRepositoryImpl(
    @Provided private val httpClient: HttpClient
): CategoryRepository {

    override suspend fun getAllCategories(): List<Category> {
        return safeApiCall {
            httpClient.get("/trends/category").body<List<CategoryDto>>().toEntity()
        }
    }

    override suspend fun isCategoriesAlreadySelectedByUser(): Boolean {
        return false // TODO("Not yet implemented")
    }

    override suspend fun updateUserInterestedCategories(categoriesIds: List<String>) {
        // TODO("Not yet implemented")
    }
}