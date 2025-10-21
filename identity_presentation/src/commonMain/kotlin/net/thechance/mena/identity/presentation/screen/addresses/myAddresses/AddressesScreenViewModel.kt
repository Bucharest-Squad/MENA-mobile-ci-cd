package net.thechance.mena.identity.presentation.screen.addresses.myAddresses

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.address_activated_successfully
import mena.identity_presentation.generated.resources.address_deleted_successfully
import mena.identity_presentation.generated.resources.error_address_not_found
import mena.identity_presentation.generated.resources.error_something_went_wrong
import mena.identity_presentation.generated.resources.is_main_address_error
import net.thechance.mena.identity.domain.repository.AddressesRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.error.ErrorState
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage
import org.jetbrains.compose.resources.StringResource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
class AddressesScreenViewModel(
    private val addressesRepository: AddressesRepository,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO

) : BaseScreenModel<AddressesScreenUIState, AddressesScreenUIEffect>(AddressesScreenUIState()),
    AddressesScreenInteractionListener {
    init {
        getUserAddresses()
    }

    override fun onBackButtonClicked() = sendNewEffect(AddressesScreenUIEffect.NavigateBack)

    override fun onAddButtonClicked() = sendNewEffect(
        AddressesScreenUIEffect.NavigateToAddressDetailsScreen(
            addressUIState = null,
            onSuccess = { onAddEditSuccess(it) })
    )

    override fun onEditAddressClicked(addressUIState: AddressUIState) =
        sendNewEffect(
            AddressesScreenUIEffect.NavigateToAddressDetailsScreen(
                addressUIState = addressUIState,
                onSuccess = {
                    onAddEditSuccess(it)
                })
        )

    override fun onClickAddress(addressId: Uuid) {
        val address = findAddressById(addressId)
        
        if (address?.isMainAddress == false) {
            tryToExecute(
                function = { addressesRepository.setActiveAddress(addressId) },
                onSuccess = {
                    getUserAddresses()
                    showSuccessAndCloseSnackBar(Res.string.address_activated_successfully)
                },
                onError = ::onErrorOccurred,
                dispatcher = dispatcher
            )
        }
    }

    override fun onDeleteAddressClicked(addressId: Uuid) = updateState {
        copy(
            deleteDialogUIState = DeleteDialogUIState(
                isVisible = true, addressId = addressId
            ),
        )
    }

    override fun onConfirmDeleteAddress() {
        val addressId = state.value.deleteDialogUIState.addressId
        val address = findAddressById(addressId)
        
        when {
            addressId == null -> showErrorAndCloseDialog(Res.string.error_address_not_found)
            address == null -> showErrorAndCloseDialog(Res.string.error_address_not_found)
            address.isMainAddress -> showErrorAndCloseDialog(Res.string.is_main_address_error)
            else -> deleteAddress(addressId)
        }
    }

    override fun onDismissDeleteDialog() = updateState {
        copy(
            deleteDialogUIState = DeleteDialogUIState(
                isVisible = false,
            )
        )
    }

    override fun onDismissSnackBar() = updateState {
        copy(
            snackBarUiState = SnackBarUiState(
                isVisible = false,
            )
        )
    }

    private fun getUserAddresses() {
        tryToExecute(
            function = {
                val addresses = addressesRepository.getUserAddresses()
                val activeAddress = addressesRepository.getActiveAddress()
                addresses.map { address ->
                    val isActive = activeAddress == address
                    address.toUiState(id = address.id, isMainAddress = isActive)
                }
            },
            onSuccess = ::onGetUserAddressesSuccess,
            onError = ::onErrorOccurred,
            dispatcher = dispatcher,
        )
    }

    private fun onGetUserAddressesSuccess(addresses: List<AddressUIState>) = updateState {
        copy(addresses = addresses, animateToCurrentLocation = true, isLoading = false)
    }

    private fun onAddEditSuccess(snackBarUiState: SnackBarUiState?) {
        updateState {
            copy(
                snackBarUiState = snackBarUiState ?: state.value.snackBarUiState,
            )
        }
        getUserAddresses()

    }

    private fun onErrorOccurred(errorState: ErrorState) {
        onDismissDeleteDialog()
        updateState {
            copy(
                snackBarUiState = SnackBarUiState(
                    snackBarType = SnackBarType.ERROR,
                    isVisible = true,
                    message = Res.string.error_something_went_wrong
                ),
                errorMessage = mapErrorToMessage(errorState)
            )
        }
    }

    private fun findAddressById(addressId: Uuid?): AddressUIState? {
        return state.value.addresses.find { it.id == addressId }
    }
    
    private fun showErrorAndCloseDialog(message: StringResource) {
        updateState {
            copy(
                snackBarUiState = SnackBarUiState(
                    snackBarType = SnackBarType.ERROR,
                    isVisible = true,
                    message = message
                ),
                deleteDialogUIState = DeleteDialogUIState(isVisible = false)
            )
        }
    }
    
    private fun showSuccessAndCloseSnackBar(message: StringResource) {
        updateState {
            copy(
                snackBarUiState = SnackBarUiState(
                    snackBarType = SnackBarType.SUCCESS,
                    isVisible = true,
                    message = message
                ),
                deleteDialogUIState = DeleteDialogUIState(isVisible = false)
            )
        }
    }
    
    private fun deleteAddress(addressId: Uuid) {
        tryToExecute(
            function = { addressesRepository.deleteAddress(addressId) },
            onSuccess = {
                getUserAddresses()
                showSuccessAndCloseSnackBar(Res.string.address_deleted_successfully)
            },
            onError = ::onErrorOccurred,
            dispatcher = dispatcher
        )
    }
}

