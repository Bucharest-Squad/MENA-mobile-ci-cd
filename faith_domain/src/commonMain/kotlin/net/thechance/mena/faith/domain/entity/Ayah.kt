package net.thechance.mena.faith.domain.entity

data class Ayah(
    val number: Int,
    val surahId: Int,
    val displayContent: String,
    val plainTextContent: String,
)
