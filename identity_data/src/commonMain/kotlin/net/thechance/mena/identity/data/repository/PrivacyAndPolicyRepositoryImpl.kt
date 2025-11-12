package net.thechance.mena.identity.data.repository

import io.ktor.client.HttpClient
import net.thechance.mena.identity.domain.model.PrivacyAndPolicySection
import net.thechance.mena.identity.domain.repository.PrivacyAndPolicyRepository

class PrivacyAndPolicyRepositoryImpl(
    private val client: HttpClient
): PrivacyAndPolicyRepository {

    override suspend fun getPrivacyAndPolicy(): List<PrivacyAndPolicySection> {
       //TODO:Call EndPoint
        return emptyList()
    }

}