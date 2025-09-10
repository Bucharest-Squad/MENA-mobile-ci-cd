package net.thechance.mena

expect object AppLogger {
    fun d(message: String, tag: String?)
    fun i(message: String, tag: String?)
    fun e(message: String, throwable: Throwable?, tag: String?)
}