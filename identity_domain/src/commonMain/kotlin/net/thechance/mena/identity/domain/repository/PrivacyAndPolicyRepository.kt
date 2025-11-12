package net.thechance.mena.identity.domain.repository

import net.thechance.mena.identity.domain.model.PrivacyAndPolicySection

interface PrivacyAndPolicyRepository {
    suspend fun getPrivacyAndPolicy(): List<PrivacyAndPolicySection>
}