package net.thechance.mena.identity.presentation.feature.location.enableLocationScreen

import net.thechance.mena.identity.presentation.base.BaseInteractionListener

interface EnableLocationScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onClickEnablePermission()
}