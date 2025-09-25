package net.thechance.mena.dukan.presentation.viewModel.createShelf

data class CreateShelfUiState(
    val shelfTitle: String = "",
    val isCreateButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val showSnackBar: Boolean = false,
    val snackBarMessage: String? = null,
    val snackBarType: SnackBarType = SnackBarType.NONE,
) {
    enum class SnackBarType {
        NONE,
        ERROR,
        SUCCESS
    }
}