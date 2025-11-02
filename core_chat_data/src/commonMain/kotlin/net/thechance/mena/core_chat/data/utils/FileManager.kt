package net.thechance.mena.core_chat.data.utils

expect class FileManager() {
    fun getDirectory(): String

    fun createDirectory(path: String): Boolean

    suspend fun writeFile(path: String, bytes: ByteArray): Boolean

    fun isFileExists(path: String): Boolean

    fun getFileSize(path: String): Long
}