package net.thechance.mena.identity.presentation.screen.register

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import net.thechance.mena.identity.presentation.base.BaseScreenModel


class RegisterScreenModel :
    BaseScreenModel<RegisterScreenUIState, RegisterScreenUIEffect>(RegisterScreenUIState()),
    RegisterScreenInteractionListener {
    override val viewModelScope: CoroutineScope
        get() = screenModelScope

    override fun onBackButtonClicked() {
        sendNewEffect(RegisterScreenUIEffect.NavigateBack)
    }
}

