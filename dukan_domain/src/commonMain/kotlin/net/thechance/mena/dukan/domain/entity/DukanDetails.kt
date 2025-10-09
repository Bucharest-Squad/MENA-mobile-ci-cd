package net.thechance.mena.dukan.domain.entity

data class DukanDetails(
    val id: String,
    val name: String,
    val imageUrl: String,
    val address: String,
    val coordinates: Coordinates,
    val color: Color,
    val style: Style
){
    data class Coordinates(
        val latitude: Double,
        val longitude: Double,
    )
    enum class Style {
        WIDE_IMAGE,
        SMALL_IMAGE,
        NO_IMAGE
    }
}