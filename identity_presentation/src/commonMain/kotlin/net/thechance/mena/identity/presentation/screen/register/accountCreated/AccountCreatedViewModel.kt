package net.thechance.mena.identity.presentation.screen.register.accountCreated

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import net.thechance.mena.identity.domain.model.AuthenticationTokens
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel

class AccountCreatedViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val authTokens: AuthenticationTokens? = null,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseScreenModel<AccountCreatedUIState, AccountCreatedUIEffect>
    (AccountCreatedUIState),
    AccountCreatedInteractionListener {

    override fun onClickGoToHome() {
        val currentAuthTokens = authTokens ?: return
        
        tryToExecute(
            function = {
                authenticationRepository.saveAuthTokensAndEmit(currentAuthTokens)
            },
            onSuccess = {
                // Navigation to home is handled automatically by EntryPoint
                // when token state changes after saveAuthTokensAndEmit
            },
            onError = { },
            dispatcher = dispatcher
        )
    }
}