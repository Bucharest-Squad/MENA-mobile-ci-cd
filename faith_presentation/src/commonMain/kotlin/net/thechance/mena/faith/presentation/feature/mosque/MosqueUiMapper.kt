package net.thechance.mena.faith.presentation.feature.mosque

import net.thechance.mena.faith.domain.entity.Mosque
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
internal fun Mosque.toUiState(distance: Double): MosqueUiState {
    return MosqueUiState(
        id = id,
        name = name,
        imageUrl = imageUrl,
        distance = distance,
        coordinate = Coordinate(
            latitude = coordinates.latitude,
            longitude = coordinates.longitude
        )
    )
}

internal fun List<Mosque>.toUiStateList(distances: List<Double>): List<MosqueUiState> {
    return this.mapIndexed { index, mosque ->
        mosque.toUiState(distance = distances.getOrNull(index) ?: 0.0)
    }
}
