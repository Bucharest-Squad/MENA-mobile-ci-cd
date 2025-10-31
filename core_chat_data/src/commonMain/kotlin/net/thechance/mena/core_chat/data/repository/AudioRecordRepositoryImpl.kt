package net.thechance.mena.core_chat.data.repository

import dev.theolm.record.Record
import dev.theolm.record.config.AudioEncoder
import dev.theolm.record.config.OutputFormat
import dev.theolm.record.config.OutputLocation
import dev.theolm.record.config.RecordConfig
import net.thechance.mena.core_chat.domain.repository.AudioRecordRepository

class AudioRecordRepositoryImpl : AudioRecordRepository {
    
    init {
        Record.setConfig(
            RecordConfig(
                outputLocation = OutputLocation.Cache,
                outputFormat = OutputFormat.MPEG_4,
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
}