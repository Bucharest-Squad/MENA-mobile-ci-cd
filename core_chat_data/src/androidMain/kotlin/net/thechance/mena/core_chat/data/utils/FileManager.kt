package net.thechance.mena.core_chat.data.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.mp.KoinPlatform.getKoin
import java.io.File

actual fun createFileManager(): FileManager {
    return AndroidFileManager()
}

class AndroidFileManager : FileManager {
    val context: Context = getKoin().get()

    private val audioCacheDir by lazy {
        File(context.cacheDir, "audio_messages").apply {
            if (!exists()) mkdirs()
        }
    }

    override fun getDirectory(): String {
        return audioCacheDir.absolutePath
    }

    override fun createDirectory(path: String): Boolean {
        val dir = File(path)
        return if (!dir.exists()) {
            dir.mkdirs()
        } else {
            true
        }
    }

    override suspend fun writeFile(path: String, bytes: ByteArray): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                File(path).writeBytes(bytes)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    override fun isFileExists(path: String): Boolean {
        return File(path).exists()
    }

    override fun getFileSize(path: String): Long {
        return try {
            File(path).length()
        } catch (e: Exception) {
            0L
        }
    }
}