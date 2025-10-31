package net.thechance.mena.core_chat.domain.repository

interface AudioRecordRepository {
    fun startRecording()
    fun stopRecording(): String
    fun isRecording(): Boolean
}