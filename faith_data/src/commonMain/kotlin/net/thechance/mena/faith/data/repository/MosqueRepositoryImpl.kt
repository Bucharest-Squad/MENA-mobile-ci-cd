package net.thechance.mena.faith.data.repository

import net.thechance.mena.faith.domain.entity.Mosque
import net.thechance.mena.faith.domain.repository.MosqueRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MosqueRepositoryImpl() : MosqueRepository {
    override suspend fun addMosque(mosque: Mosque) {
        // TODO("Not yet implemented")
    }

    override suspend fun getNearbyMosques(
        latitude: Double,
        longitude: Double,
        radius: Double
    ): List<Mosque> {
        return fakeMosquesList
    }

    override suspend fun getMosquesByName(query: String): List<Mosque> {
        return emptyList()
    }

    @OptIn(ExperimentalUuidApi::class)
    private val fakeMosquesList: List<Mosque> = listOf(
        Mosque(
            id = Uuid.random(),
            name = "Al Farooq Omar Bin Al Khattab Mosque",
            coordinates = Mosque.Coordinates(25.1554, 55.2256),
            address = "Jumeirah, Dubai, UAE",
            imageUrl = "https://images.unsplash.com/photo-1549640376-113e6ca1db1c"
        ),
        Mosque(
            id = Uuid.random(),
            name = "Jumeirah Mosque",
            coordinates = Mosque.Coordinates(25.2285, 55.2647),
            address = "Jumeirah 1, Dubai, UAE",
            imageUrl = "https://images.unsplash.com/photo-1542551774-3a7b2b4f9c14"
        ),
        Mosque(
            id = Uuid.random(),
            name = "Al Salam Mosque",
            coordinates = Mosque.Coordinates(25.0917, 55.1540),
            address = "Al Barsha, Dubai, UAE",
            imageUrl = "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee"
        ),
        Mosque(
            id = Uuid.random(),
            name = "Imam Hussein Mosque",
            coordinates = Mosque.Coordinates(25.2438, 55.3075),
            address = "Al Satwa, Dubai, UAE",
            imageUrl = "https://images.unsplash.com/photo-1496317556649-f930d733eea6"
        ),
        Mosque(
            id = Uuid.random(),
            name = "Al Noor Mosque",
            coordinates = Mosque.Coordinates(25.3463, 55.3794),
            address = "Sharjah, UAE",
            imageUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb"
        )
    )
}
