package net.thechance.mena.trends.presentation.screen.interestpick

import org.jetbrains.compose.resources.StringResource

sealed interface InterestsPickUiEffect {
    data object NavigateBack : InterestsPickUiEffect
    data object NavigateToSave : InterestsPickUiEffect
    data class ShowError(val message: StringResource) : InterestsPickUiEffect
}