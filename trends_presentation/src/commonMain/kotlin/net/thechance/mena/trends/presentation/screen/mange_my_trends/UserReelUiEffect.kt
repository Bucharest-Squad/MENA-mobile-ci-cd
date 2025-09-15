package net.thechance.mena.trends.presentation.screen.mange_my_trends

sealed interface UserReelUiEffect {
    data object NavigateBack : UserReelUiEffect
}