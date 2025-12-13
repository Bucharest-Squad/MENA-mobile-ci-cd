package net.thechance.mena.identity.presentation.feature.location.locationManagement

import net.thechance.mena.identity.domain.entity.AddressType
import net.thechance.mena.identity.presentation.base.BaseInteractionListener

interface LocationManagementScreenInteractionListener : BaseInteractionListener {
    fun onClickMap()
    fun onClickEdit()
    fun onClickBack()
    fun onClickAddressType(addressType: AddressType)
    fun onClickSave()
    fun onChangeOtherAddressType(newType: String)
}