package net.thechance.mena.core_chat.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.thechance.mena.core_chat.presentation.components.SnackBarData
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ChatEffectorImpl() : ChatEffector {
    private val _chatEffect = Channel<ChatEffect>(Channel.BUFFERED)
    override val chatEffect = _chatEffect.receiveAsFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L

    @OptIn(ExperimentalTime::class)
    override suspend fun navigate(route: ChatRoute, navOptions: NavOptions?, forceNavigate: Boolean) {
        mutex.withLock {
            val now = Clock.System.now().toEpochMilliseconds()
            if (forceNavigate || now - lastNavigateTime >= EFFECT_DEBOUNCE_MS) {
                lastNavigateTime = now
                _chatEffect.send(
                    ChatEffect.Navigate(route = route, navOptions = navOptions)
                )
            }
        }
    }

    override suspend fun popBackStack(vararg arguments: Pair<String, Any>) {
        _chatEffect.send(ChatEffect.PopBackStack(arguments.toMap()))
    }

    override suspend fun showSnackBar(snackBarData: SnackBarData) {
        _chatEffect.send(ChatEffect.ShowSnackBar(snackBarData))
    }

    companion object {
        private const val EFFECT_DEBOUNCE_MS = 500L
    }
}