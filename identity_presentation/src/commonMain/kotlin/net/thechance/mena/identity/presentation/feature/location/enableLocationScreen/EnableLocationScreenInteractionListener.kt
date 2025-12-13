package net.thechance.mena.identity.presentation.feature.location.enableLocationScreen

import net.thechance.mena.identity.presentation.core.base.BaseInteractionListener

interface EnableLocationScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onClickEnablePermission()
}