package net.thechance.mena.faith.presentation.feature.qiblah.compass

import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.util.StringResourceProvider
import net.thechance.mena.faith.presentation.util.ResourceProvider

class CompassViewModel(
    override val resourceProvider: ResourceProvider = StringResourceProvider()
) : BaseViewModel<CompassScreenState, CompassEffect>(CompassScreenState(),resourceProvider),
    CompassInteractionListener {

    override fun onBackClick() = sendEffect(CompassEffect.NavigateBack)
}