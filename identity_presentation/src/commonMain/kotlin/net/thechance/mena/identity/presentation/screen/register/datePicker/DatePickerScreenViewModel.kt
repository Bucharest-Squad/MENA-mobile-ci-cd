package net.thechance.mena.identity.presentation.screen.register.datePicker

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.LocalDate
import net.thechance.mena.identity.domain.entity.PhoneNumber
import net.thechance.mena.identity.domain.useCase.validation.age.AgeValidator
import net.thechance.mena.identity.presentation.base.BaseScreenModel

class DatePickerScreenViewModel(
    private val ageValidator: AgeValidator,
    private val phoneNumber: PhoneNumber,
    private val firstName: String,
    private val lastName: String,
    private val username: String,
    private val password: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    BaseScreenModel<DatePickerScreenUIState, DatePickerScreenUIEffect>(
        DatePickerScreenUIState()
    ), DatePickerScreenInteractionListener {

    override fun onClickNext() {
        val selectedDate = state.value.selectedDate ?: return
        sendNewEffect(
            DatePickerScreenUIEffect.NavigateToSelectGender(
                phoneNumber = phoneNumber,
                firstName = firstName,
                lastName = lastName,
                username = username,
                password = password,
                birthDate = selectedDate
            )
        )
    }

    override fun onChangeDate(day: Int, month: Int, year: Int) {
        updateState { copy(selectedDate = LocalDate(year, month, day)) }
        updateNextButtonState()
    }

    private fun updateNextButtonState() {
        val selectedDate = state.value.selectedDate
        val isAgeValid = selectedDate.let { ageValidator.isValid(it) }
        updateState { copy(isNextEnabled = isAgeValid) }
    }
}