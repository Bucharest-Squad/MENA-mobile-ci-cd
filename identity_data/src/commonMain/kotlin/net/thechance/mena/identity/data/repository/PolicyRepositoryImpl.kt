package net.thechance.mena.identity.data.repository

import net.thechance.mena.identity.domain.model.PolicySection
import net.thechance.mena.identity.domain.repository.PolicyRepository

class PolicyRepositoryImpl: PolicyRepository {

    override fun getPolicyAndPolicy(): List<PolicySection> {
       //TODO:Call EndPoint
        return emptyList()
    }

}