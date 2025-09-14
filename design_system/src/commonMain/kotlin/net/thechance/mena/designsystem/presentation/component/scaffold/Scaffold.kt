package net.thechance.mena.designsystem.presentation.component.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp

@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    overlays: ScaffoldScope .() -> Unit = {},
    content: @Composable () -> Unit,
) {
    val scope = remember { ScaffoldScopeImpl() }.apply {
        items.clear()
        overlays()
    }

    val hasBlur = scope.items.any { it.isVisible }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = topBar,
        bottomBar = bottomBar
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .then(
                    if (hasBlur) Modifier.blur(4.dp) else Modifier
                )
                .navigationBarsPadding()
                .systemBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }

        scope.items.forEach {
            if (it.isVisible)
                it.content(scope)
        }
    }
}