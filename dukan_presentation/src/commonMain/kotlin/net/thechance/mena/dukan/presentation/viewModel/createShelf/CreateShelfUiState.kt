package net.thechance.mena.dukan.presentation.viewModel.createShelf

data class CreateShelfUiState(
    val shelfTitle: String = "",
    val isCreateButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val showSnackBar: Boolean = false,
    val snackBarType: SnackBarType = SnackBarType.NONE,
){
    enum class SnackBarType {
        NONE,
        INVALID_NAME,
        NAME_EXISTS,
        CREATE_FAILED
    }
}