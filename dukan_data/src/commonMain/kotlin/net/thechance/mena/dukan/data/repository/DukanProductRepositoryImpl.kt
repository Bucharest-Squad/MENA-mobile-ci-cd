package net.thechance.mena.dukan.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import net.thechance.mena.dukan.data.repository.dto.product.CreateProductResponse
import net.thechance.mena.dukan.data.repository.mapper.toCreateProductRequest
import net.thechance.mena.dukan.data.repository.util.buildMultiPartFormData
import net.thechance.mena.dukan.data.repository.util.safeApiCall
import net.thechance.mena.dukan.domain.entity.Product
import net.thechance.mena.dukan.domain.repository.ProductRepository
import net.thechance.mena.dukan.domain.util.PagedFetchResponse

class DukanProductRepositoryImpl(
    private val client: HttpClient
): ProductRepository {

    override suspend fun createProduct(product: Product): String {
        return safeApiCall<CreateProductResponse> {
            client.post("${BASE_URL}/create") {
                contentType(ContentType.Application.Json)
                setBody(product.toCreateProductRequest())
            }
        }.productId
    }

    override suspend fun getProductsByShelfId(
        shelfId: String,
        page: Int,
        size: Int
    ): PagedFetchResponse<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadProductImages(
        fileName: List<String>,
        fileBytes: List<ByteArray>,
        productId: String
    ): List<String> {
        return safeApiCall {
            client.post("${BASE_URL}/images/$productId") {
                setBody(buildMultiPartFormData(fileName, fileBytes,"files"))
            }
        }
    }


    companion object{
        private const val BASE_URL = "/dukan/product"

    }
}