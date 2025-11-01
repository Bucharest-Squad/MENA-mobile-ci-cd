package net.thechance.mena.admin_panel.presentation.screen.admin_panel.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.thechance.mena.admin_panel.presentation.screen.admin_panel.AdminPanelInteractionListener
import net.thechance.mena.admin_panel.presentation.screen.admin_panel.AdminPanelScreenState
import net.thechance.mena.admin_panel.resources.Res
import net.thechance.mena.admin_panel.resources.file_selected
import net.thechance.mena.admin_panel.resources.file_unselected
import net.thechance.mena.admin_panel.resources.shop_selected
import net.thechance.mena.admin_panel.resources.shop_unselected
import net.thechance.mena.admin_panel.resources.users_selected
import net.thechance.mena.admin_panel.resources.users_unselected
import net.thechance.mena.admin_panel.resources.wallet_selected
import net.thechance.mena.admin_panel.resources.wallet_unselected
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource

@Composable
fun AdminSideBarTabs(
    selectedTab: AdminPanelScreenState.CurrentTab,
    interactionListener: AdminPanelInteractionListener,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AdminSidebarItem(
            title = "Dukan Management",
            selectedIcon = painterResource(Res.drawable.shop_selected),
            notSelectedIcon = painterResource(Res.drawable.shop_unselected),
            isSelected = selectedTab == AdminPanelScreenState.CurrentTab.DUKAN_MANAGEMENT,
            onClick = interactionListener::onDukanManagementClicked
        )
        AdminSidebarItem(
            title = "Dukan Requests",
            selectedIcon = painterResource(Res.drawable.file_selected),
            notSelectedIcon = painterResource(Res.drawable.file_unselected),
            isSelected = selectedTab == AdminPanelScreenState.CurrentTab.DUKAN_REQUEST,
            onClick = interactionListener::onDukanRequestClicked
        )
        AdminSidebarItem(
            title = "Deposit",
            selectedIcon = painterResource(Res.drawable.wallet_selected),
            notSelectedIcon = painterResource(Res.drawable.wallet_unselected),
            isSelected = selectedTab == AdminPanelScreenState.CurrentTab.DEPOSIT,
            onClick = interactionListener::onDepositClicked
        )
        AdminSidebarItem(
            title = "Users Management",
            selectedIcon = painterResource(Res.drawable.users_selected),
            notSelectedIcon = painterResource(Res.drawable.users_unselected),
            isSelected = selectedTab == AdminPanelScreenState.CurrentTab.USERS,
            onClick = interactionListener::onUsersManagementClicked
        )
    }
}

@Composable
private fun AdminSidebarItem(
    title: String,
    selectedIcon: Painter,
    notSelectedIcon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedTitleColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colorScheme.shadeSecondary else Theme.colorScheme.brand.brand
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Crossfade(targetState = isSelected)
        { selected ->
            Icon(
                painter = if (selected) selectedIcon else notSelectedIcon,
                contentDescription = title,
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            text = title,
            style = Theme.typography.label.small,
            color = animatedTitleColor,
            textAlign = TextAlign.Center
        )
    }
}