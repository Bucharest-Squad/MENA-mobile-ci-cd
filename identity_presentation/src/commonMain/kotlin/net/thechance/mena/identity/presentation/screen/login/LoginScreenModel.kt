package net.thechance.mena.identity.presentation.screen.login

import kotlinx.coroutines.CoroutineScope
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import cafe.adriel.voyager.core.model.screenModelScope

class LoginScreenModel :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {
    override val viewModelScope: CoroutineScope
        get() = screenModelScope

}