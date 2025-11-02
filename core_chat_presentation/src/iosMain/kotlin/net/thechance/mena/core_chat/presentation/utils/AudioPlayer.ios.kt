@file:OptIn(ExperimentalAtomicApi::class)

package net.thechance.mena.core_chat.presentation.utils

import io.github.vinceglb.filekit.utils.toByteArray
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioPlayerDelegateProtocol
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSData
import platform.Foundation.dataWithContentsOfFile
import platform.darwin.NSObject
import kotlin.concurrent.atomics.ExperimentalAtomicApi

actual fun createAudioPlayer(onError: (String) -> Unit): AudioPlayer {
    return IOSAudioPlayer(onError)
}

actual fun convertAudioFileToByteArray(filePath: String): ByteArray {
    return runCatching {
        NSData.dataWithContentsOfFile(filePath)?.toByteArray() ?: byteArrayOf()
    }.getOrDefault(byteArrayOf())
}

@OptIn(ExperimentalForeignApi::class)
class IOSAudioPlayer(private val onError: (String) -> Unit) : AudioPlayer {
    private var audioPlayer: AVAudioPlayer? = null
    private var currentFilePath: String? = null

    override fun play(filePath: String) {
        try {
            if (audioPlayer != null && currentFilePath == filePath && !audioPlayer!!.isPlaying()) {
                audioPlayer?.play()
                return
            }

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

                player.delegate = object : NSObject(), AVAudioPlayerDelegateProtocol {
                    override fun audioPlayerDidFinishPlaying(player: AVAudioPlayer, successfully: Boolean) {
                        player.pause()
                    }
                }
            } else {
                onError("Failed to create AVAudioPlayer")
            }
            currentFilePath = filePath
        } catch (e: Exception) {
            onError("Failed to play audio: ${e.message}")
        }
    }
    
    override fun pause() {
        if (audioPlayer?.isPlaying() == true) {
            audioPlayer?.pause()
        }
    }

    override fun stop() {
        audioPlayer?.apply {
            if (isPlaying()) stop()
        }
        audioPlayer = null
        currentFilePath = null
    }

    override fun release() {
        stop()
    }

    override fun getDuration(filePath: String): Long {
        return try {
            val fileManager = NSFileManager.defaultManager
            if (!fileManager.fileExistsAtPath(filePath)) {
                return 0L
            }

            val url = NSURL.fileURLWithPath(filePath)
            val player = AVAudioPlayer(url, null)
            (player?.duration?.toLong() ?: 0L)// Convert to seconds
        } catch (e: Exception) {
            onError("Failed to get audio duration: ${e.message}")
            0L
        }
    }

    override fun getCurrentPosition(): Long {
        return try {
            (audioPlayer?.currentTime?.toLong() ?: 0L)
        } catch (e: Exception) {
            onError("Failed to get current position: ${e.message}")
            0L
        }
    }
}