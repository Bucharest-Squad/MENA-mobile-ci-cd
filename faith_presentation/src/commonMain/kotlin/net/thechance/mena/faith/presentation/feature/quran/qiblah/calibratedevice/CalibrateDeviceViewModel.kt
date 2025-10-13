package net.thechance.mena.faith.presentation.feature.quran.qiblah.calibratedevice

import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.util.StringResourceProvider
import net.thechance.mena.faith.presentation.util.ResourceProvider

class CalibrateDeviceViewModel(
    override val resourceProvider: ResourceProvider = StringResourceProvider()
) :
    BaseViewModel<Unit, CalibrateDeviceEffect>(Unit, resourceProvider),
    CalibrateDeviceInteractionListener {

    override fun onBackClick() = sendEffect(CalibrateDeviceEffect.NavigateBack)
    override fun onContinueClick() = sendEffect(CalibrateDeviceEffect.NavigateToQiblah)

}
