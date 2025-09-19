package net.thechance.mena.trends.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import net.thechance.mena.trends.data.dto.CategoryResponseDto
import net.thechance.mena.trends.data.dto.SubmitInterestsRequestDto
import net.thechance.mena.trends.data.mapper.toEntity
import net.thechance.mena.trends.data.util.NetworkConstants.CATEGORY
import net.thechance.mena.trends.data.util.NetworkConstants.INTERESTS
import net.thechance.mena.trends.data.util.NetworkConstants.TRENDS
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
            httpClient.get("/$TRENDS/$CATEGORY").body<CategoryResponseDto>()
                .categories.toEntity()
        }
    }

    override suspend fun isCategoriesAlreadySelectedByUser(): Boolean {
        return safeApiCall {
            httpClient.get("/$TRENDS/$CATEGORY") // TODO: put string of end point
                .body<CategoryResponseDto>().categories.isNotEmpty()
        }
    }

    override suspend fun updateUserInterestedCategories(categoriesIds: List<String>) {
        safeApiCall {
            httpClient.post("/$TRENDS/$INTERESTS") {
                setBody(SubmitInterestsRequestDto(categoriesIds))
            }
        }
    }
}