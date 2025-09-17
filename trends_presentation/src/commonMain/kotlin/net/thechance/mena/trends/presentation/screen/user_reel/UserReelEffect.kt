package net.thechance.mena.trends.presentation.screen.user_reel

sealed interface UserReelEffect {
    data object NavigateBack : UserReelEffect
}