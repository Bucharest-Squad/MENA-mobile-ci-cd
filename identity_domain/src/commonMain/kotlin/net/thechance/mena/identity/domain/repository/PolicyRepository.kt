package net.thechance.mena.identity.domain.repository

import net.thechance.mena.identity.domain.model.PolicySection

interface PolicyRepository {
    suspend fun getPrivacyAndPolicy(): List<PolicySection>
}