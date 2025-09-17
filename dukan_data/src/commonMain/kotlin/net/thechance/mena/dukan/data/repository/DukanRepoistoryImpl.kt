package net.thechance.mena.dukan.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import net.thechance.mena.dukan.data.repository.dto.DukanCategoryDto
import net.thechance.mena.dukan.data.repository.dto.DukanColorDto
import net.thechance.mena.dukan.data.repository.dto.MyDukanStatusDto
import net.thechance.mena.dukan.data.repository.mapper.toCreateDukanRequest
import net.thechance.mena.dukan.data.repository.mapper.toEntity
import net.thechance.mena.dukan.data.repository.util.safeApiCall
import net.thechance.mena.dukan.domain.entity.Category
import net.thechance.mena.dukan.domain.entity.Dukan
import net.thechance.mena.dukan.domain.entity.DukanColor
import net.thechance.mena.dukan.domain.entity.MyDukanStatus
import net.thechance.mena.dukan.domain.repository.DukanRepository

class DukanRepositoryImpl(
    private val client: HttpClient
) : DukanRepository {

    override suspend fun createDukan(dukan: Dukan) {
        safeApiCall {
            client.post(
                urlString = "$BASE_URL/create"
            ) {
                setBody(dukan.toCreateDukanRequest())
            }
        }
    }

    override suspend fun getDukanStyles(): List<Dukan.Style> {
        return safeApiCall {
            client.get(
                urlString = "$BASE_URL/styles"
            ).body<List<String>>().map {
                Dukan.Style.valueOf(it)
            }
        }
    }

    override suspend fun getCategories(): List<Category> {
        return safeApiCall {
            client.get(
                urlString = "$BASE_URL/categories"
            ).body<List<DukanCategoryDto>>().toEntity()
        }
    }

    override suspend fun getDukanColors(): List<DukanColor> {
        return safeApiCall {
            client.get(
                urlString = "$BASE_URL/colors"
            ).body<List<DukanColorDto>>().toEntity()
        }
    }

    override suspend fun getMyDukanStatus(): MyDukanStatus {
        return safeApiCall {
            client.get(
                urlString = "$BASE_URL/statues"
            ).body<MyDukanStatusDto>().toEntity()
        }
    }

    override suspend fun isDukanNameTaken(name: String): Boolean {
        return safeApiCall { client.get("$BASE_URL/available?name=$name").body() }
    }

    companion object {
        private const val BASE_URL = "/dukan"
    }
}