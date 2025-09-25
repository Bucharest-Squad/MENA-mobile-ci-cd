package net.thechance.mena.dukan.presentation.viewModel.manageShelf

import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanEffect

sealed interface ManageShelfEffect {
    data object NavigateBack : ManageShelfEffect
    data class DeleteShelf(val shelfId: String) : ManageShelfEffect
}