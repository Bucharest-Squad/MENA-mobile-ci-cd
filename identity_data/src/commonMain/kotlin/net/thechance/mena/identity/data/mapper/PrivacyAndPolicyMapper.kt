package net.thechance.mena.identity.data.mapper

import net.thechance.mena.identity.data.dto.privacyAndPolicy.response.PrivacyAndPolicyResponseDto
import net.thechance.mena.identity.data.dto.privacyAndPolicy.response.SectionResponseDto
import net.thechance.mena.identity.domain.model.PrivacyAndPolicy
import net.thechance.mena.identity.domain.model.Section

fun PrivacyAndPolicyResponseDto.toDomain(): PrivacyAndPolicy{
    return PrivacyAndPolicy(
        updateDate = this.updateDate,
        sections = this.sections?.map { it.toDomain() } ?: emptyList()
    )
}

fun SectionResponseDto.toDomain(): Section{
    return Section(
        title = this.title,
        content = this.content
    )
}