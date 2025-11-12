package net.thechance.mena.identity.data.repository

import net.thechance.mena.identity.domain.model.PolicySection
import net.thechance.mena.identity.domain.repository.PolicyRepository

class PolicyRepositoryImpl: PolicyRepository {

    override suspend fun getPrivacyAndPolicy(): List<PolicySection> {
       //TODO:Call EndPoint
        return emptyList()
    }

}