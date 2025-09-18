package net.thechance.mena.trends.domain.util

import org.koin.core.annotation.Single

@Single(binds = [Logger::class])
class LoggerDefault : Logger {
    override fun logError(title: String, message: String) {
        println("${RED}$title: $message${RESET}")
    }

    private companion object {
        const val RESET = "\u001B[0m"
        const val RED = "\u001B[31m"
        const val GREEN = "\u001B[32m"
        const val YELLOW = "\u001B[33m"
        const val BLUE = "\u001B[34m"
    }
}