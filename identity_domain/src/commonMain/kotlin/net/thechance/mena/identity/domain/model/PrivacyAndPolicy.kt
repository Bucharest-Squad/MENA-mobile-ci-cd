package net.thechance.mena.identity.domain.model

data class PrivacyAndPolicy(
    val updateDate: String?,
    val sections: List<Section>
)
data class Section(
    val title:String,
    val content:String
)