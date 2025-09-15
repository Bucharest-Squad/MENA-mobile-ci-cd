package net.thechance.mena.wallet.presentation.util

fun formatBalance(balance: Double): String {
        return String.format(Locale.US, "%,.2f", balance)

}