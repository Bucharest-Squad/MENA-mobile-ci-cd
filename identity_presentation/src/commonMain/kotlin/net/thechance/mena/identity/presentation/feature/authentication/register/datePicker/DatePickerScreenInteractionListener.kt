package net.thechance.mena.identity.presentation.feature.authentication.register.datePicker

import net.thechance.mena.identity.presentation.base.BaseInteractionListener

interface DatePickerScreenInteractionListener : BaseInteractionListener {
    fun onClickNext()
    fun onChangeDate(day: Int, month: Int, year: Int)
}