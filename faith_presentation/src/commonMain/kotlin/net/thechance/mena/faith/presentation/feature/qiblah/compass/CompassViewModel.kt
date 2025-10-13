package net.thechance.mena.faith.presentation.feature.qiblah.compass

import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.util.DefaultResourceProvider
import net.thechance.mena.faith.presentation.util.ResourceProvider

class CompassViewModel(
    override val resourceProvider: ResourceProvider = DefaultResourceProvider()
) : BaseViewModel<CompassScreenState, CompassEffect>(CompassScreenState(),resourceProvider),
    CompassInteractionListener {

    override fun onBackClick() = sendEffect(CompassEffect.NavigateBack)
}