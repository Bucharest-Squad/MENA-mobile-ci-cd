package net.thechance.mena.core_chat.data.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.mp.KoinPlatform.getKoin
import java.io.File

actual class FileManager {
    val context: Context = getKoin().get()

    private val audioCacheDir by lazy {
        File(context.cacheDir, "audio_messages").apply {
            if (!exists()) mkdirs()
        }
    }

    actual fun getDirectory(): String {
        return audioCacheDir.absolutePath
    }

    actual fun createDirectory(path: String): Boolean {
        val dir = File(path)
        return if (!dir.exists()) {
            dir.mkdirs()
        } else {
            true
        }
    }

    actual suspend fun writeFile(path: String, bytes: ByteArray): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                File(path).writeBytes(bytes)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    actual fun isFileExists(path: String): Boolean {
        return File(path).exists()
    }

    actual fun getFileSize(path: String): Long {
        return try {
            File(path).length()
        } catch (e: Exception) {
            0L
        }
    }
}