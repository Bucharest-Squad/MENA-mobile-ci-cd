package net.thechance.mena.core_chat.presentation.utils

import android.media.MediaPlayer
import java.io.File

actual fun createAudioPlayer(onError: (String) -> Unit): AudioPlayer {
    return AndroidAudioPlayer(onError)
}

class AndroidAudioPlayer(private val onError: (String) -> Unit) : AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    override fun play(filePath: String) {
        try {
            stop()
            
            if (!File(filePath).exists()) {
                onError("File does not exist: $filePath")
                return
            }
            
            mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepareAsync()
                setOnPreparedListener { start() }
                setOnErrorListener { _, what, extra ->
                    onError("MediaPlayer error: what=$what, extra=$extra")
                    false
                }
                setOnCompletionListener {
                    stop()
                }
            }
        } catch (e: Exception) {
            onError("Failed to play audio: ${e.message}")
        }
    }

    override fun stop() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) stop()
                reset()
                release()
            }
        } catch (e: Exception) {
            onError("Error stopping media player: ${e.message}")
        } finally {
            mediaPlayer = null
        }
    }

    override fun release() { stop() }
}