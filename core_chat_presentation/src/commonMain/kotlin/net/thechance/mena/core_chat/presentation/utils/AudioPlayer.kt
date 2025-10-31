package net.thechance.mena.core_chat.presentation.utils

interface AudioPlayer {
    fun play(filePath: String)
    fun stop()
    fun release()
}

expect fun createAudioPlayer(onError: (String) -> Unit): AudioPlayer