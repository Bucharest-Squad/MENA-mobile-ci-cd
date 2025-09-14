@file:OptIn(ExperimentalComposeUiApi::class)

package net.thechance.mena.designsystem.presentation.component.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.appBar.HomeAppBar
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.scaffold.ScaffoldScope
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScope.BottomSheet(
    dismissOnBackPress: Boolean = true,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Theme.colorScheme.background.surface,
    scrimColor: Color = Theme.colorScheme.primary.primary.copy(0.55f),
    cornerShape: Shape = RoundedCornerShape(
        topStart = Theme.radius.xl,
        topEnd = Theme.radius.xl
    ),
    paddingFromTop: Dp = 64.dp,
    skipPartiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.(state: SheetState) -> Unit
) {

    val state = rememberModalBottomSheetState(skipPartiallyExpanded)

    ModalBottomSheet(
        modifier = modifier
            .fillMaxSize()
            .padding(top = paddingFromTop),
        sheetState = state,
        onDismissRequest = onDismiss,
        shape = cornerShape,
        scrimColor = scrimColor,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = dismissOnBackPress
        ),
        containerColor = containerColor
    ) {
        content(state)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BottomSheetPreview() {
    MenaTheme {

        var showBottomSheet by remember { mutableStateOf(false) }

        Scaffold(
            overlays = {
                bottomSheet(showBottomSheet) {
                    BottomSheet(
                        onDismiss = {
                            showBottomSheet = false
                        },
                        content = {
                            LazyColumn {
                                items(10) {
                                    Text("HI", style = Theme.typography.title.large)
                                }
                            }
                        }
                    )
                }
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        onClick = {
                            showBottomSheet = true
                        }
                    )
                    .background(Theme.colorScheme.background.surface).fillMaxSize()
            ) {
                HomeAppBar("202")
                Text(
                    text = "HI",
                    style = Theme.typography.title.large,
                )
            }
        }
    }
}
