package net.thechance.mena.faith.presentation.feature.mosque

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal data class NearbyMosquesMapUiState(
    val mosques: List<MosqueUiState> = emptyList(),
    val mosquesSearchResults: List<MosqueUiState> = emptyList(),
    val centerOfMap: Coordinate? = null,
    val isLoading: Boolean = true,
    val isMosqueBottomSheetVisible: Boolean = false,
    val isSearchResultsBottomSheetVisible: Boolean = false,
    val isSearchButtonVisible: Boolean = false,
    val error: String? = null,
    val query: String = "",
)

@OptIn(ExperimentalUuidApi::class)
internal data class MosqueUiState(
    val id: Uuid,
    val name: String,
    val imageUrl: String,
    val distance: Double,
    val coordinate: Coordinate
)

internal data class Coordinate(
    val latitude: Double,
    val longitude: Double,
)

@OptIn(ExperimentalUuidApi::class)
internal val fakeMosqueList = listOf(
    MosqueUiState(
        id = Uuid.parse("1e6f8a10-7dec-11d0-a765-00a0c91e6bf1"),
        name = "Al-Fath Mosque",
        imageUrl = "https://example.com/images/alfath.jpg",
        distance = 0.8,
        coordinate = Coordinate(30.0444, 31.2357)
    ),
    MosqueUiState(
        id = Uuid.parse("1e6f8a11-7dec-11d0-a765-00a0c91e6bf2"),
        name = "Al-Azhar Mosque",
        imageUrl = "https://example.com/images/alazhar.jpg",
        distance = 2.3,
        coordinate = Coordinate(30.0459, 31.2625)
    ),
    MosqueUiState(
        id = Uuid.parse("1e6f8a12-7dec-11d0-a765-00a0c91e6bf3"),
        name = "Amr Ibn Al-As Mosque",
        imageUrl = "https://example.com/images/amribnalas.jpg",
        distance = 4.1,
        coordinate = Coordinate(30.0061, 31.2315)
    ),
    MosqueUiState(
        id = Uuid.parse("1e6f8a13-7dec-11d0-a765-00a0c91e6bf4"),
        name = "Sultan Hassan Mosque",
        imageUrl = "https://example.com/images/sultanhassan.jpg",
        distance = 3.7,
        coordinate = Coordinate(30.0299, 31.2593)
    ),
    MosqueUiState(
        id = Uuid.parse("1e6f8a14-7dec-11d0-a765-00a0c91e6bf5"),
        name = "Mohamed Ali Mosque",
        imageUrl = "https://example.com/images/mohamedali.jpg",
        distance = 5.5,
        coordinate = Coordinate(30.0286, 31.2599)
    )
)
