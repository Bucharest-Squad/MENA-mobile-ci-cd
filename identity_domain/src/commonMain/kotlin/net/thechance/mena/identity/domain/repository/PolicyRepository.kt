package net.thechance.mena.identity.domain.repository

import net.thechance.mena.identity.domain.model.PolicySection

interface PolicyRepository {
    fun getPolicyAndPolicy(): List<PolicySection>
}