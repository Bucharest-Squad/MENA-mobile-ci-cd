package net.thechance.mena.designsystem.presentation.component.segment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Segment(
    modifier: Modifier = Modifier,
    content: SegmentScope.() -> Unit
) {
    val scope = remember { SegmentScopeImpl() }
        .apply {
            items.clear()
            content()
        }
    var selectedSegment by remember { mutableStateOf(0) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.md))
                .background(Theme.colorScheme.background.surfaceHigh)
        ) {
            scope.items.forEachIndexed { index, item ->
                SegmentButton(
                    item.title,
                    item.title == scope.items[selectedSegment].title,
                    onSelectChange = { selectedSegment = index }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        scope.items.getOrNull(selectedSegment)?.content(scope)
    }
}

@Preview
@Composable
private fun SegmentPreview() {
    MenaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colorScheme.background.surface)
                .padding(horizontal = 36.dp, vertical = 16.dp)
        ) {
            Segment {
                item("My Trends") {
                    Text(
                        "My Trends",
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxSize()
                    )
                }
                item("Favorite") {
                    Text(
                        "Option2",
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxSize()
                            .background(Theme.colorScheme.background.surfaceHigh)
                    )
                }
            }
        }
    }
}