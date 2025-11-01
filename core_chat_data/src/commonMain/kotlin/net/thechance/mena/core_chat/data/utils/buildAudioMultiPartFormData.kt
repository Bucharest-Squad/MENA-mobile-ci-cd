package net.thechance.mena.core_chat.data.utils

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

fun Pair<String, ByteArray>.buildAudioMultiPartFormData(
    fieldName: String,
    chatId: String
): MultiPartFormDataContent {
    val (name, byteArray) = this
    val extension = byteArray.getAudioExtension()
    val formattedFileName = formatFileName(name, extension)

    return MultiPartFormDataContent(
        formData {
            append("chatId", chatId)
            append(
                fieldName,
                byteArray,
                Headers.build {
                    append(HttpHeaders.ContentType, audioExtensionToMimeType(extension))
                    append(HttpHeaders.ContentDisposition, """filename="$formattedFileName"""")
                }
            )
        }
    )
}

private fun ByteArray.getAudioExtension(): String = when {
    size >= 8 &&
            get(4) == 0x66.toByte() &&
            get(5) == 0x74.toByte() &&
            get(6) == 0x79.toByte() &&
            get(7) == 0x70.toByte() -> "m4a"

    size >= 2 &&
            get(0) == 0xFF.toByte() &&
            (get(1) == 0xFB.toByte() || get(1) == 0xF3.toByte()) -> "mp3"

    else -> "m4a"
}

private fun audioExtensionToMimeType(extension: String): String = when (extension.lowercase()) {
    "m4a" -> "audio/m4a"
    "mp3" -> "audio/mpeg"
    "aac" -> "audio/aac"
    else -> "application/octet-stream"
}

private fun formatFileName(name: String, extension: String): String {
    val nameWithExtension = if (name.contains('.')) name else "$name.${extension.ifBlank { "bin" }}"
    return nameWithExtension
        .replace("\"", "")
        .replace("\r", "")
        .replace("\n", "")
        .replace(";", "_")
        .replace("+", "_")
        .take(150)
}