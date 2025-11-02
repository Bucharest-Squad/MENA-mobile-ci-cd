package net.thechance.mena.core_chat.data.repository

import dev.theolm.record.Record
import dev.theolm.record.config.AudioEncoder
import dev.theolm.record.config.OutputFormat
import dev.theolm.record.config.OutputLocation
import dev.theolm.record.config.RecordConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import net.thechance.mena.core_chat.data.utils.FileManager
import net.thechance.mena.core_chat.data.utils.tryNetworkCall
import net.thechance.mena.core_chat.domain.exception.AudioFileNotFound
import net.thechance.mena.core_chat.domain.exception.InvalidAudioFile
import net.thechance.mena.core_chat.domain.exception.NotFoundException
import net.thechance.mena.core_chat.domain.exception.SaveAudioFailed
import net.thechance.mena.core_chat.domain.repository.AudioRecordRepository
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class AudioRecordRepositoryImpl(
    private val client: HttpClient,
    private val fileManager: FileManager,
) : AudioRecordRepository {

    init {
        Record.setConfig(
            RecordConfig(
                outputLocation = OutputLocation.Cache,
                outputFormat = OutputFormat.WAV,
                audioEncoder = AudioEncoder.AAC,
                sampleRate = 44100
            )
        )
    }

    override fun startRecording() {
        Record.startRecording()
    }

    override fun stopRecording(): String {
        return Record.stopRecording()
    }

    override fun isRecording(): Boolean {
        return Record.isRecording()
    }

    override suspend fun getAudioFilePath(url: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val localPath = downloadAudioFile(url)

                validateAudioFile(localPath)

                localPath

            } catch (e: Exception) {
                throw e
            }
        }
    }

    private suspend fun downloadAudioFile(audioUrl: String): String {
        val cacheDir = fileManager.getDirectory()
        val fileName = "audio_${audioUrl.hashCode().toString().replace("-", "")}.m4a"
        val cachedFilePath = "$cacheDir/$fileName"

        if (fileManager.isFileExists(cachedFilePath)) {
            return cachedFilePath
        }

        val audioBytes = tryNetworkCall<ByteArray>(
            bodyType = typeInfo<ByteArray>(),
        ) {
            client.get(audioUrl)
        } ?: throw NotFoundException("Audio file not found on server")

        val success = fileManager.writeFile(cachedFilePath, audioBytes)
        if (!success) {
            throw SaveAudioFailed("Failed to save audio")
        }

        return cachedFilePath
    }

    private fun validateAudioFile(filePath: String) {
        if (!fileManager.isFileExists(filePath)) {
            throw AudioFileNotFound("Audio file not found: $filePath")
        }

        if (fileManager.getFileSize(filePath) == 0L) {
            throw InvalidAudioFile("Audio file is empty: $filePath")
        }
    }
}