package net.thechance.mena.faith.presentation.qibla

import kotlinx.coroutines.flow.Flow


interface AzimuthProvider {
    val azimuthFlow: Flow<Float>
    fun startListening()
    fun stopListening()
}
