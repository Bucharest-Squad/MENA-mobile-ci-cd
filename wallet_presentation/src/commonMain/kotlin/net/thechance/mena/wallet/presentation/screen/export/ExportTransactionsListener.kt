package net.thechance.mena.wallet.presentation.screen.export

interface ExportTransactionsListener {
    fun onBackClicked()
    fun onAllTransactionsClicked()
    fun onCustomFilteringClicked()
    fun onViewAndShareClicked()
    fun onDownloadClicked()
}