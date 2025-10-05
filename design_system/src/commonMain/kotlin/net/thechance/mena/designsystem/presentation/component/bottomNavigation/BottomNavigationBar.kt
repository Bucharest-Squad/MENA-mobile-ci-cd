package net.thechance.mena.designsystem.presentation.component.bottomNavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mena.design_system.generated.resources.Res
import mena.design_system.generated.resources.dukan
import mena.design_system.generated.resources.home
import mena.design_system.generated.resources.ic_dukan
import mena.design_system.generated.resources.ic_dukan_selected
import mena.design_system.generated.resources.ic_home
import mena.design_system.generated.resources.ic_home_selected
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable BottomNavigationScope.() -> Unit = {},
) {
    val scope = remember { BottomNavigationScopeImpl() }.apply {
        items.clear()
        content()
    }

    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }


    BottomNavigationBarContent(
        items = scope.items,
        selectedItemIndex = selectedItemIndex,
        onItemClick = {
            selectedItemIndex = scope.items.indexOf(it)
            scope.items[selectedItemIndex].entry.invoke()
        },
        modifier = modifier.background(Theme.colorScheme.background.surfaceLow)
    )
}

@Preview
@Composable
private fun PreviewBottomNavigationBar() {
    MenaTheme {
        Box(Modifier.fillMaxSize()) {
            BottomNavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                bottomNavigationItem(
                    selectedIcon = painterResource(Res.drawable.ic_home_selected),
                    notSelectedIcon = painterResource(Res.drawable.ic_home),
                    title = stringResource(Res.string.home),
                    entry = { }
                )

                bottomNavigationItem(
                    selectedIcon = painterResource(Res.drawable.ic_dukan_selected),
                    notSelectedIcon = painterResource(Res.drawable.ic_dukan),
                    title = stringResource(Res.string.dukan),
                    entry = { }
                )
            }
        }
    }
}