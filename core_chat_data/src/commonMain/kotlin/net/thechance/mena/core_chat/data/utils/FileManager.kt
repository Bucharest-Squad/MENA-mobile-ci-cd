package net.thechance.mena.core_chat.data.utils

interface FileManager {
    fun getDirectory(): String
    fun createDirectory(path: String): Boolean
    suspend fun writeFile(path: String, bytes: ByteArray): Boolean
    fun isFileExists(path: String): Boolean
    fun getFileSize(path: String): Long
}

expect fun createFileManager() : FileManager