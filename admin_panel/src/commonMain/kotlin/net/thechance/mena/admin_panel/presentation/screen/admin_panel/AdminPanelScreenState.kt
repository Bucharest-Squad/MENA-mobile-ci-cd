package net.thechance.mena.admin_panel.presentation.screen.admin_panel

import net.thechance.mena.admin_panel.navigation.AdminPanelRoute
import net.thechance.mena.admin_panel.navigation.Deposit
import net.thechance.mena.admin_panel.navigation.DukanManagement
import net.thechance.mena.admin_panel.navigation.DukanRequests
import net.thechance.mena.admin_panel.navigation.UsersManagement
import net.thechance.mena.admin_panel.resources.Res
import net.thechance.mena.admin_panel.resources.deposit
import net.thechance.mena.admin_panel.resources.dukan_management
import net.thechance.mena.admin_panel.resources.dukan_requests
import net.thechance.mena.admin_panel.resources.file_selected
import net.thechance.mena.admin_panel.resources.file_unselected
import net.thechance.mena.admin_panel.resources.shop_selected
import net.thechance.mena.admin_panel.resources.shop_unselected
import net.thechance.mena.admin_panel.resources.users_management
import net.thechance.mena.admin_panel.resources.users_selected
import net.thechance.mena.admin_panel.resources.users_unselected
import net.thechance.mena.admin_panel.resources.wallet_selected
import net.thechance.mena.admin_panel.resources.wallet_unselected
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class AdminPanelScreenState(
    val isLogOutDialogShown: Boolean = false,
    val currentTab: CurrentTab = CurrentTab.USERS_MANAGEMENT,
) {
    enum class CurrentTab(
        val route: AdminPanelRoute,
        val title: StringResource,
        val selectedIconRes: DrawableResource,
        val unSelectedIconRes: DrawableResource
    ) {
        USERS_MANAGEMENT(
            route = UsersManagement,
            title = Res.string.users_management,
            selectedIconRes = Res.drawable.users_selected,
            unSelectedIconRes = Res.drawable.users_unselected
        ),
        DUKAN_MANAGEMENT(
            route = DukanManagement,
            title = Res.string.dukan_management,
            selectedIconRes = Res.drawable.shop_selected,
            unSelectedIconRes = Res.drawable.shop_unselected
        ),
        DUKAN_REQUEST(
            route = DukanRequests,
            title = Res.string.dukan_requests,
            selectedIconRes = Res.drawable.file_selected,
            unSelectedIconRes = Res.drawable.file_unselected
        ),
        DEPOSIT(
            route = Deposit,
            title = Res.string.deposit,
            selectedIconRes = Res.drawable.wallet_selected,
            unSelectedIconRes = Res.drawable.wallet_unselected
        )
    }
}