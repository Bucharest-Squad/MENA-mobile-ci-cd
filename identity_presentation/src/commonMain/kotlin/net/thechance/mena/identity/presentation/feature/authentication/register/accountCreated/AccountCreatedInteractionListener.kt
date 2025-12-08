package net.thechance.mena.identity.presentation.feature.authentication.register.accountCreated

import net.thechance.mena.identity.presentation.base.BaseInteractionListener

interface AccountCreatedInteractionListener : BaseInteractionListener {
    fun onClickGoToHome()
}