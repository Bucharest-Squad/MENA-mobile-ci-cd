package net.thechance.mena.trends.presentation.screen.mange_my_trends

sealed interface UserReelEffect {
    data object NavigateBack : UserReelEffect
}