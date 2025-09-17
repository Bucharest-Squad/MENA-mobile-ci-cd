package net.thechance.mena.core_chat.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ChatEffectorImpl() : ChatEffector {
    private val _navigateEvent = Channel<ChatEffect>(Channel.BUFFERED)
    override val chatEffect = _navigateEvent.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    @OptIn(ExperimentalTime::class)
    override suspend fun navigate(route: ChatRoute, navOptions: NavOptions?, forceNavigate: Boolean) {
        mutex.withLock {
            val now = Clock.System.now().toEpochMilliseconds()
            if (forceNavigate || now - lastNavigateTime >= EFFECT_DEBOUNCE_MS) {
                lastNavigateTime = now
                _navigateEvent.send(
                    ChatEffect.Navigate(route = route, navOptions = navOptions)
                )
            }
        }
    }

    override suspend fun popBackStack(vararg arguments: Pair<String, Any>) {
        _navigateEvent.send(ChatEffect.PopBackStack(arguments.toMap()))
    }

    override suspend fun showSnackBar(message: String) {
        _navigateEvent.send(ChatEffect.ShowSnackBar(message))
    }

    companion object {
        private const val EFFECT_DEBOUNCE_MS = 500L
    }
}