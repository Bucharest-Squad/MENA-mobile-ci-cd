package net.thechance.mena.dukan.presentation.screen.manageDukan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.thechance.mena.dukan.presentation.navigation.DukanRoute
import net.thechance.mena.dukan.presentation.navigation.LocalNavController
import net.thechance.mena.dukan.presentation.screen.manageDukan.content.ManageDukanContent
import net.thechance.mena.dukan.presentation.screen.manageShelf.ManageShelfArgs
import net.thechance.mena.dukan.presentation.util.ObserveAsEffect
import net.thechance.mena.dukan.presentation.viewModel.manageDukan.ManageDukanEffect
import net.thechance.mena.dukan.presentation.viewModel.manageDukan.ManageDukanViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManageDukanScreen(
    viewModel: ManageDukanViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    val deletedShelfId = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<String?>(ManageShelfArgs.deletedShelfId, null)
        ?.collectAsStateWithLifecycle()

    LaunchedEffect(deletedShelfId?.value != null) {
        viewModel.onShowDeleteShelfConfirmationDialog()
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.remove<String>(ManageShelfArgs.deletedShelfId)
    }

    ObserveAsEffect(viewModel.effect) { effect ->
        when (effect) {
            ManageDukanEffect.NavigateBack -> navController.popBackStack()

            ManageDukanEffect.NavigateToAddShelf -> navController.navigate(
                DukanRoute.CreateShelfScreenRoute
            )

            ManageDukanEffect.NavigateToManageShelf -> {
            }

            ManageDukanEffect.NavigateToAddProduct -> {
            }

            ManageDukanEffect.NavigateToProductDetails -> {
            }
        }
    }
    ManageDukanContent(
        state = state,
        listener = viewModel,
        deletedShelfId = deletedShelfId?.value
    )
}
