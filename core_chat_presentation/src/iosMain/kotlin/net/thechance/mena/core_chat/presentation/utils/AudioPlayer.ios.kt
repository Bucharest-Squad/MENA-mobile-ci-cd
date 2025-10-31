package net.thechance.mena.core_chat.presentation.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL

actual fun createAudioPlayer(onError: (String) -> Unit): AudioPlayer {
    return IOSAudioPlayer(onError)
}

@OptIn(ExperimentalForeignApi::class)
class IOSAudioPlayer(private val onError: (String) -> Unit) : AudioPlayer {
    private var audioPlayer: AVAudioPlayer? = null

    override fun play(filePath: String) {
        try {
            stop()
            
            val fileManager = NSFileManager.defaultManager
            if (!fileManager.fileExistsAtPath(filePath)) {
                onError("File does not exist: $filePath")
                return
            }
            
            val url = NSURL.fileURLWithPath(filePath)
            val player = AVAudioPlayer(url, null)
            
            if (player != null) {
                audioPlayer = player
                player.play()
            } else {
                onError("Failed to create AVAudioPlayer")
            }
        } catch (e: Exception) {
            onError("Failed to play audio: ${e.message}")
        }
    }

    override fun stop() {
        audioPlayer?.apply {
            if (isPlaying()) stop()
        }
        audioPlayer = null
    }

    override fun release() {
        stop()
    }
}