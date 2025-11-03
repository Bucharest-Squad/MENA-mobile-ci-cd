package net.thechance.mena.admin_panel.presentation.screen.mainContainer.component

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
import net.thechance.mena.admin_panel.presentation.screen.mainContainer.MainContainerScreenState
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AdminSideBarTabs(

    selectedTab: MainContainerScreenState.CurrentTab,
    onTabSelected: (tab: MainContainerScreenState.CurrentTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AdminSidebarItem(
            title = stringResource(MainContainerScreenState.CurrentTab.DUKAN_MANAGEMENT.title),
            selectedIcon = painterResource(MainContainerScreenState.CurrentTab.DUKAN_MANAGEMENT.selectedIconRes),
            notSelectedIcon = painterResource(MainContainerScreenState.CurrentTab.DUKAN_MANAGEMENT.unSelectedIconRes),
            isSelected = selectedTab == MainContainerScreenState.CurrentTab.DUKAN_MANAGEMENT,
            onClick = { onTabSelected(MainContainerScreenState.CurrentTab.DUKAN_MANAGEMENT) }
        )

        AdminSidebarItem(
            title = stringResource(MainContainerScreenState.CurrentTab.DUKAN_REQUEST.title),
            selectedIcon = painterResource(MainContainerScreenState.CurrentTab.DUKAN_REQUEST.selectedIconRes),
            notSelectedIcon = painterResource(MainContainerScreenState.CurrentTab.DUKAN_REQUEST.unSelectedIconRes),
            isSelected = selectedTab == MainContainerScreenState.CurrentTab.DUKAN_REQUEST,
            onClick = { onTabSelected(MainContainerScreenState.CurrentTab.DUKAN_REQUEST) }
        )

        AdminSidebarItem(
            title = stringResource(MainContainerScreenState.CurrentTab.DEPOSIT.title),
            selectedIcon = painterResource(MainContainerScreenState.CurrentTab.DEPOSIT.selectedIconRes),
            notSelectedIcon = painterResource(MainContainerScreenState.CurrentTab.DEPOSIT.unSelectedIconRes),
            isSelected = selectedTab == MainContainerScreenState.CurrentTab.DEPOSIT,
            onClick = { onTabSelected(MainContainerScreenState.CurrentTab.DEPOSIT) }
        )

        AdminSidebarItem(
            title = stringResource(MainContainerScreenState.CurrentTab.USERS_MANAGEMENT.title),
            selectedIcon = painterResource(MainContainerScreenState.CurrentTab.USERS_MANAGEMENT.selectedIconRes),
            notSelectedIcon = painterResource(MainContainerScreenState.CurrentTab.USERS_MANAGEMENT.unSelectedIconRes),
            isSelected = selectedTab == MainContainerScreenState.CurrentTab.USERS_MANAGEMENT,
            onClick = { onTabSelected(MainContainerScreenState.CurrentTab.USERS_MANAGEMENT) }
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
        targetValue =
            if (isSelected)
                Theme.colorScheme.shadeSecondary
            else
                Theme.colorScheme.brand.brand
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