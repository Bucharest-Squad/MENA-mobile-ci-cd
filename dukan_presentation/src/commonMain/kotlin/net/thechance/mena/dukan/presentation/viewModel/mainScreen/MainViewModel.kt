package net.thechance.mena.dukan.presentation.viewModel.mainScreen

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.error_general
import mena.dukan_presentation.generated.resources.no_internet_connection
import net.thechance.mena.dukan.domain.exceptions.NoInternetException
import net.thechance.mena.dukan.domain.exceptions.NoSuchItemException
import net.thechance.mena.dukan.domain.repository.DukanDiscoveryRepository
import net.thechance.mena.dukan.domain.repository.DukanManagementRepository
import net.thechance.mena.dukan.presentation.component.shared.SnackBarType
import net.thechance.mena.dukan.presentation.component.shared.SnackBarUiState
import net.thechance.mena.dukan.presentation.viewModel.base.BaseViewModel
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanUiState.DukanCategoryUiState
import net.thechance.mena.dukan.presentation.viewModel.createDukan.toUiState
import net.thechance.mena.dukan.presentation.viewModel.mainScreen.MainScreenUiState.DukanStatusUi

import org.jetbrains.compose.resources.StringResource

class MainViewModel(
    private val dukanManagementRepository: DukanManagementRepository,
    private val dukanDiscoveryRepository: DukanDiscoveryRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<MainScreenUiState, MainScreenEffect>(
    initialState = MainScreenUiState(),
    defaultDispatcher = dispatcher
), MainInteractionListener {

    private var fetchJob: Job? = null

    init {
        fetchData()
    }

    private fun fetchData() {
        if (fetchJob?.isActive == true) return
        fetchJob = viewModelScope.launch(defaultDispatcher) {
            getDukanState()
        }.also { job ->
            job.invokeOnCompletion { fetchJob = null }
        }
    }

    private fun getDukanState() {
        tryToExecute(
            onStart = ::setLoadingState,
            block = ::getDukanStateBlock,
            onSuccess = ::onGetDukanStateSuccess,
            onError = ::onGetDukanStateError
        )
    }

    private fun setLoadingState() {
        updateState { copy(dukanState = MainScreenUiState.DukanState(status = DukanStatusUi.Loading)) }
    }

    private suspend fun getDukanStateBlock(): MainScreenUiState.DukanState? {
        return dukanManagementRepository.getMyDukanStatus()?.toUiState()
    }

    private fun onGetDukanStateSuccess(dukanState: MainScreenUiState.DukanState?) {
        if (dukanState == null) {
            updateState {
                copy(
                    dukanState = MainScreenUiState.DukanState(status = DukanStatusUi.None),
                    isConnected = true,
                    snackBarState = null
                )
            }
        } else {
            updateState { copy(dukanState = dukanState, isConnected = true) }
            if (dukanState.status == DukanStatusUi.Approved) {
                getCategories()
                loadEditorPicksDukans()
                loadBestNearestDukans()
            }
        }
    }

    private fun onGetDukanStateError(error: Throwable) {
        when (error) {
            is NoSuchItemException -> updateState {
                copy(
                    dukanState = MainScreenUiState.DukanState(status = DukanStatusUi.None)
                )
            }

            is NoInternetException -> {
                updateToNoInternetState()
            }

            else -> {
                showSnackBar(message = Res.string.error_general, type = SnackBarType.ERROR)
            }
        }
    }

    private fun loadEditorPicksDukans() {
        tryToCollect(
            block = ::createLoadEditorPagingSource,
            onCollect = ::onLoadedEditorPicksDukan,
            onError = ::handleNetworkError
        )
    }

    private fun createLoadEditorPagingSource(): Flow<PagingData<MainScreenUiState.EditorPickDukanUiState>> {
        return createPagingSourceFlow(mapper = { it.toEditorPickUiState() }) { currentPage, pageSize ->
            dukanDiscoveryRepository.getEditorPicksDukans(
                page = currentPage,
                size = pageSize
            ).items
        }
    }

    private fun onLoadedEditorPicksDukan(dukans: PagingData<MainScreenUiState.EditorPickDukanUiState>) {
        updateState {
            copy(
                editorPickDukans = flowOf(dukans),
                snackBarState = null
            )
        }
    }

    private fun loadBestNearestDukans() {
        tryToCollect(
            block = ::createLoadBestDukanPagingSource,
            onCollect = ::onLoadedBestNearestDukans,
            onError = ::handleNetworkError
        )
    }

    private fun createLoadBestDukanPagingSource(): Flow<PagingData<MainScreenUiState.BestNearestDukanUiState>> {
        return createPagingSourceFlow(mapper = { it.toBestNearestUiState() }) { currentPage, pageSize ->
            dukanDiscoveryRepository.getBestAroundDukans(
                page = currentPage,
                size = pageSize
            ).items
        }
    }

    private fun onLoadedBestNearestDukans(dukans: PagingData<MainScreenUiState.BestNearestDukanUiState>) {
        updateState {
            copy(
                bestNearestDukans = flowOf(dukans),
                snackBarState = null
            )
        }
    }

    private fun handleNetworkError(error: Throwable) {
        when (error) {
            is NoInternetException -> {
                updateToNoInternetState()
            }

            else -> {
                showSnackBar(
                    message = Res.string.error_general,
                    type = SnackBarType.ERROR
                )
            }
        }
    }

    private fun getCategories() {
        tryToExecute(
            onStart = {
                setLoadingState()
            },
            block = ::getCategoriesBlock,
            onSuccess = ::onGetCategoriesSuccess,
            onError = ::handleNetworkError
        )
    }

    private suspend fun getCategoriesBlock(): List<DukanCategoryUiState> {
        return dukanManagementRepository.getCategories().toUiState()
    }

    private fun onGetCategoriesSuccess(categoryUiState: List<DukanCategoryUiState>) {
        updateState {
            copy(
                categories = categoryUiState,
                snackBarState = null
            )
        }
    }

    private fun updateToNoInternetState() {
        showSnackBar(message = Res.string.no_internet_connection, type = SnackBarType.ERROR)
        updateState {
            copy(isConnected = false)
        }
    }

    override fun onDukanButtonClicked() {
        when (state.value.dukanState.status) {
            DukanStatusUi.None -> emitEffect(MainScreenEffect.NavigateToAddDukanScreen)
            DukanStatusUi.Pending -> emitEffect(MainScreenEffect.NavigateToPendingDukanScreen)
            DukanStatusUi.Approved -> emitEffect(MainScreenEffect.NavigateToManageDukanScreen)
            DukanStatusUi.Loading -> {}
        }
    }

    override fun onViewMoreClicked() {
        emitEffect(MainScreenEffect.NavigateToDukansCategoriesScreen)
    }

    override fun onRetryClicked() {
        fetchData()
    }

    override fun onSnackBarDismissed() {
        updateState {
            copy(snackBarState = null)
        }
    }

    override fun onSelectedCategoryClicked(categoryId: String, categoryName: String) {
        emitEffect(MainScreenEffect.NavigateToDukansScreenByCategory(categoryId, categoryName))
    }

    override fun onNearestDukanClicked(dukanId: String) {
        emitEffect(MainScreenEffect.NavigateToSelectedDukan(dukanId))
    }

    override fun onEditorPickDukanClicked(dukanId: String) {
        emitEffect(MainScreenEffect.NavigateToSelectedDukan(dukanId))
    }

    private fun showSnackBar(message: StringResource, type: SnackBarType) {
        updateState {
            copy(
                snackBarState = SnackBarUiState(
                    message = message,
                    snackBarType = type
                )
            )
        }
    }
}