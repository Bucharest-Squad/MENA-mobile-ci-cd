package net.thechance.mena.core_chat.data.utils

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSFileSize
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.writeToFile

@OptIn(ExperimentalForeignApi::class)
actual class FileManager {

    private val fileManager = NSFileManager.defaultManager

    private val audioCacheDir: String by lazy {
        val cacheDir = fileManager.URLsForDirectory(
            NSCachesDirectory,
            NSUserDomainMask
        ).firstOrNull() as? NSURL

        val audioDir = cacheDir?.URLByAppendingPathComponent("audio_messages")
        val path = audioDir?.path ?: ""

        createDirectory(path)
        path
    }

    actual fun getDirectory(): String {
        return audioCacheDir
    }

    actual fun createDirectory(path: String): Boolean {
        return try {
            if (!isFileExists(path)) {
                fileManager.createDirectoryAtPath(
                    path = path,
                    withIntermediateDirectories = true,
                    attributes = null,
                    error = null
                )
            } else {
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    actual suspend fun writeFile(path: String, bytes: ByteArray): Boolean {
        return try {
            val data = bytes.toNSData()
            data.writeToFile(path, atomically = true)
        } catch (e: Exception) {
            false
        }
    }

    actual fun isFileExists(path: String): Boolean {
        return fileManager.fileExistsAtPath(path)
    }

    actual fun getFileSize(path: String): Long {
        return try {
            val attributes = fileManager.attributesOfItemAtPath(path, error = null)
            (attributes?.get(NSFileSize) as? NSNumber)?.longValue ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    @OptIn(BetaInteropApi::class)
    private fun ByteArray.toNSData(): NSData {
        if (isEmpty()) {
            return NSData()
        }

        return usePinned { pinned ->
            NSData.create(
                bytes = pinned.addressOf(0),
                length = this.size.toULong()
            )
        }
    }
}