package net.thechance.mena.trends.presentation.shared.util.save_state

import org.koin.core.annotation.Single


@Single(binds = [CustomSaveStateHandle::class])
class CustomSaveState : CustomSaveStateHandle {
    private val map = mutableMapOf<String, Any?>()

    override fun <T> get(key: String): T? {
        return map[key] as T?
    }

    override fun <T> set(key: String, value: T) {
        map[key] = value
    }
}
