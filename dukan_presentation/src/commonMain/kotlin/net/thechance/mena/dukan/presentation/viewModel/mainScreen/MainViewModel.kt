package net.thechance.mena.dukan.presentation.viewModel.mainScreen

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import net.thechance.mena.dukan.domain.exceptions.NoInternetException
import net.thechance.mena.dukan.domain.exceptions.NoSuchItemException
import net.thechance.mena.dukan.domain.repository.DukanDiscoveryRepository
import net.thechance.mena.dukan.domain.repository.DukanManagementRepository
import net.thechance.mena.dukan.presentation.util.pagination.Pager
import net.thechance.mena.dukan.presentation.util.pagination.PagingData
import net.thechance.mena.dukan.presentation.util.pagination.base.createPagingSource
import net.thechance.mena.dukan.presentation.viewModel.base.BaseViewModel
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanUiState.DukanCategoryUiState
import net.thechance.mena.dukan.presentation.viewModel.createDukan.toUiState
import net.thechance.mena.dukan.presentation.viewModel.mainScreen.MainScreenUiState.DukanStatusUi

class MainViewModel(
    private val dukanManagementRepository: DukanManagementRepository,
    private val dukanDiscoveryRepository: DukanDiscoveryRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<MainScreenUiState, MainScreenEffect>(
    initialState = MainScreenUiState(),
    defaultDispatcher = dispatcher
), MainInteractionListener {
    lateinit var bestNearestDukanPager: Pager<Int, MainScreenUiState.BestNearestDukanUiState>
    lateinit var editorPickDukanPager: Pager<Int, MainScreenUiState.EditorPickDukanUiState>

    init {
        fetchData()
    }

    private fun fetchData() {
        getDukanState()
        initPagers()
        getCategories()
        loadEditorPicksDukans()
        loadBestNearestDukans()
    }

    private fun loadEditorPicksDukans() {
        tryToExecute(
            onStart = ::onEditorPickDukanLoading,
            block = {editorPickDukanPager.load()},
            onSuccess = { collectEditorPickData() },
            onError = ::handleNetworkError
        )
    }

    private fun collectEditorPickData() {
        tryToCollect(
            block = { editorPickDukanPager.flow },
            onCollect = ::onLoadedEditorPicksDukan,
        )
    }



    private fun onEditorPickDukanLoading(){
        updateState {
            copy(
                editorPickDukanState = MainScreenUiState.EditorPickDukanStatus.LOADING,
                editorPickDukans = PagingData()
            )
        }
    }

    private fun onLoadedEditorPicksDukan(dukans: PagingData<MainScreenUiState.EditorPickDukanUiState>) {
        val loadedBestNearestDukans = when {
            dukans.isLoading && dukans.items.isEmpty() -> MainScreenUiState.EditorPickDukanStatus.LOADING
            else -> MainScreenUiState.EditorPickDukanStatus.LOADED
        }
        updateState {
            copy(
                editorPickDukans = dukans,
                editorPickDukanState = loadedBestNearestDukans
            )
        }
    }

    private fun loadBestNearestDukans() {
        tryToExecute(
            onStart = ::onBestNearestDukanLoading,
            block = { bestNearestDukanPager.load() },
            onSuccess = {collectBestNearestData()},
            onError = ::handleNetworkError
        )
    }

    private fun collectBestNearestData() {
        tryToCollect(
            block = { bestNearestDukanPager.flow },
            onCollect = ::onLoadedBestNearestDukans
        )
    }





    private fun onBestNearestDukanLoading() {
        updateState {
            copy(
                bestNearestDukans = PagingData(),
                bestNearestDukanState = MainScreenUiState.BestNearestDukanStatus.LOADING
            )
        }
    }

    private fun onLoadedBestNearestDukans(dukans: PagingData<MainScreenUiState.BestNearestDukanUiState>) {
        val loadedBestNearestDukans = when {
            dukans.isLoading && dukans.items.isEmpty() -> MainScreenUiState.BestNearestDukanStatus.LOADING
            else -> MainScreenUiState.BestNearestDukanStatus.LOADED
        }
        updateState {
            copy(
                bestNearestDukans = dukans,
                bestNearestDukanState = loadedBestNearestDukans
            )
        }
    }


    private fun handleNetworkError(error: Throwable) {
        when (error) {
            is NoInternetException -> updateState {
                copy(
                    isConnected = false,
                )
            }
            else -> updateState {
                copy(
                    errorMessage = error.message
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
                categories = categoryUiState
            )
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
            updateState { copy(dukanState = MainScreenUiState.DukanState(status = DukanStatusUi.None), isConnected = true) }
        } else {
            updateState { copy(dukanState = dukanState, isConnected = true) }
        }
    }

    private fun onGetDukanStateError(error: Throwable) {
        when (error) {
            is NoSuchItemException -> updateState {
                copy(
                    errorMessage = error.message,
                    dukanState = MainScreenUiState.DukanState(
                        status = DukanStatusUi.None
                    )
                )
            }
            is NoInternetException->{
                updateState {
                    copy(
                        isConnected = false,
                        dukanState = MainScreenUiState.DukanState(status = DukanStatusUi.None)
                    )
                }
            }
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

    override fun onViewMoreButtonClick() {
        emitEffect(MainScreenEffect.NavigateCategoryToScreen)
    }

    override fun onRetryButtonClicked() {
        fetchData()
    }

    override fun onCategorySelectedClick(categoryId: String, categoryName: String) {
        emitEffect(MainScreenEffect.NavigateToDukansScreenByCategory(categoryId, categoryName))
    }

    override fun onNearestDukanClick(dukanId: String) {
        emitEffect(MainScreenEffect.NavigateSelectedDukan(dukanId))
    }

    override fun onEditorPickDukanClick(dukanId: String) {
        emitEffect(MainScreenEffect.NavigateSelectedDukan(dukanId))
    }

    fun initPagers() {
        bestNearestDukanPager = createPagingSource(
            mapper = { it.toBestNearestUiState() }
        ) { currentPage ->
            dukanDiscoveryRepository.getBestAroundDukans(
                page = currentPage,
                size = 5
            )
        }

        editorPickDukanPager = createPagingSource(
            mapper = { it.toEditorPickUiState() }
        ) { currentPage ->
            dukanDiscoveryRepository.getEditorPicksDukans(
                page = currentPage,
                size = 5
            )
        }
    }

}