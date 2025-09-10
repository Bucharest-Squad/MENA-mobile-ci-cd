package net.thechance.mena

import android.util.Log

actual object AppLogger {
    actual fun d(message: String, tag: String?) {
        Log.d(tag ?: "TAG", message)
    }
    actual fun i(message: String, tag: String?) {
        Log.i(tag ?: "TAG", message)
    }
    actual fun e(message: String, throwable: Throwable?, tag: String?) {
        Log.e(tag ?: "TAG", message, throwable)
    }
}