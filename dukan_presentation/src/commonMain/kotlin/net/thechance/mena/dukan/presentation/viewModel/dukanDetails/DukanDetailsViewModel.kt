package net.thechance.mena.dukan.presentation.viewModel.dukanDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.thechance.mena.dukan.domain.entity.DukanDetails
import net.thechance.mena.dukan.domain.repository.DukanRepository
import net.thechance.mena.dukan.domain.repository.ProductRepository
import net.thechance.mena.dukan.domain.repository.ShelfRepository
import net.thechance.mena.dukan.presentation.screen.dukanDetails.DukanDetailsArgs
import net.thechance.mena.dukan.presentation.util.pagination.Pager
import net.thechance.mena.dukan.presentation.util.pagination.PagingData
import net.thechance.mena.dukan.presentation.util.pagination.base.createPagingSource
import net.thechance.mena.dukan.presentation.viewModel.base.BaseViewModel

class DukanDetailsViewModel(
    private val dukanRepository: DukanRepository,
    private val shelfRepository: ShelfRepository,
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<DukanDetailsUiState, DukanDetailsEffects>(DukanDetailsUiState()),
    DukanDetailsInteractionListener {
    val dukanId: String = requireNotNull(savedStateHandle[DukanDetailsArgs.DUKAN_ID])

    init {
        loadDukanDetails()
        collectShelves()
    }

    private fun loadDukanDetails() {
        tryToExecute(
            block = {
                dukanRepository.getDukanDetailsByDukanId(dukanId)
            },
            onSuccess = ::onLoadDukanDetailsSuccess,
            onError = ::onLoadDukanDetailsError
        )
    }

    private fun onLoadDukanDetailsSuccess(dukanDetails: DukanDetails) {
        updateState {
            copy(
                dukanInfo = dukanDetails.toUiState(),
                isDukanInfoLoading = false
            )
        }
    }

    private fun onLoadDukanDetailsError(throwable: Throwable) {
        updateState {
            copy(
                isDukanInfoLoading = false,
                errorMessage = throwable.message
            )
        }
    }

    private fun collectShelves() {
        tryToCollect(
            block = { pagerShelf.flow },
            onCollect = ::onShelvesLoaded
        )
        viewModelScope.launch {
            pagerShelf.load()
        }
    }

    private fun onShelvesLoaded(shelves: PagingData<DukanDetailsUiState.ShelfUiState>) {
        val shelfState = when {
            shelves.isLoading && shelves.items.isEmpty() -> DukanDetailsUiState.ShelvesState.LOADING
            shelves.items.isEmpty() -> DukanDetailsUiState.ShelvesState.EMPTY
            else -> DukanDetailsUiState.ShelvesState.LOADED
        }
        updateState {
            copy(
                shelves = shelves,
                shelvesState = shelfState,
                shelfIdSelected = shelves.items.firstOrNull()?.id.orEmpty()
            )
        }
    }


    private val nestedProductPagers =
        mutableMapOf<String, Pager<Int, DukanDetailsUiState.ProductUiState>>()

    private fun getProductsPager(shelfId: String): Pager<Int, DukanDetailsUiState.ProductUiState> {
        return nestedProductPagers.getOrPut(shelfId) {
            createPagingSource(
                mapper = { it.toUiState() }
            ) { pageNumber ->
                productRepository.getProductsByShelfId(
                    shelfId = shelfId,
                    page = pageNumber,
                    size = 20
                )
            }
        }
    }

    override fun onBackClicked() {
        emitEffect(DukanDetailsEffects.NavigateBack)
    }

    override fun onShelfClicked(id: String): Pager<Int, DukanDetailsUiState.ProductUiState> {
        updateState {
            copy(shelfIdSelected = id)
        }
        return getProductsPager(id)
    }


    override fun onViewAllShelfProductsClicked(id: String, name: String) {
        emitEffect(DukanDetailsEffects.NavigateToViewAllShelfProducts(id, name))
    }

    override fun onViewDukanOnMapClicked(latitude: Double, longitude: Double) {
        emitEffect(DukanDetailsEffects.NavigateToViewDukanOnMap(latitude, longitude))
    }

    override fun productsShelfView(id: String): Pager<Int, DukanDetailsUiState.ProductUiState> {
        return getProductsPager(id)
    }

    val pagerShelf = createPagingSource(
        mapper = { it.toUiState() }
    ) {
        shelfRepository.getShelvesByDukanId(
            dukanId = dukanId,
            page = it,
            pageSize = 20
        )
    }
}