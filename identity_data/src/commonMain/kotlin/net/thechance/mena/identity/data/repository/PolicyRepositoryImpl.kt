package net.thechance.mena.identity.data.repository

import io.ktor.client.HttpClient
import net.thechance.mena.identity.domain.model.PolicySection
import net.thechance.mena.identity.domain.repository.PolicyRepository

class PolicyRepositoryImpl(
    private val client: HttpClient
): PolicyRepository {

    override suspend fun getPrivacyAndPolicy(): List<PolicySection> {
       //TODO:Call EndPoint
        return emptyList()
    }

}