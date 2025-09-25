package net.thechance.mena.trends.presentation.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mena.trends_presentation.generated.resources.Res
import mena.trends_presentation.generated.resources.arrow_reload_horizontal
import mena.trends_presentation.generated.resources.error
import mena.trends_presentation.generated.resources.ic_cancel
import mena.trends_presentation.generated.resources.ic_delete
import mena.trends_presentation.generated.resources.loading
import mena.trends_presentation.generated.resources.retry
import mena.trends_presentation.generated.resources.success
import mena.trends_presentation.generated.resources.thumbnail
import mena.trends_presentation.generated.resources.upload_failed
import mena.trends_presentation.generated.resources.video_02
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.progressBar.ProgressBar
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun VideoLoadingCardItem(
    title: String,
    sizeText: String = "",
    state: ItemState,
    progress: Float? = null,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit = {},
    onRetry: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Theme.colorScheme.primary.onPrimary)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .weight(1f),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Theme.colorScheme.brand.brandVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.video_02),
                    contentDescription = stringResource(Res.string.thumbnail),
                    tint = Theme.colorScheme.brand.brand,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(bottom = 12.dp),
            ) {
                Text(
                    text = title,
                    style = Theme.typography.label.medium,
                    color = Theme.colorScheme.primary.primary,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )

                when (state) {
                    ItemState.Loading -> {
                        Text(
                            text = sizeText,
                            color = Theme.colorScheme.shadeSecondary,
                            style = Theme.typography.label.extraSmall,
                            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                        )
                        //TODO we will handle it after talk with infra team
                        ProgressBar(
                            modifier = Modifier
                                .padding(start = 8.dp, end = 12.dp)
                                .fillMaxWidth(),
                            color = Theme.colorScheme.brand.brand
                        )
                    }

                    ItemState.Success -> {
                        Text(
                            text = sizeText,
                            color = Theme.colorScheme.shadeSecondary,
                            style = Theme.typography.label.extraSmall,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    ItemState.Error -> {
                        Text(
                            text = stringResource(Res.string.upload_failed),
                            color = Theme.colorScheme.border.error,
                            style = Theme.typography.label.extraSmall,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                        )
                    }
                }
            }
        }

        when (state) {
            ItemState.Loading -> {
                Row(modifier = Modifier.padding(top = 12.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_cancel),
                        contentDescription = stringResource(Res.string.loading),
                        tint = Theme.colorScheme.shadeSecondary,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onCancel() }
                    )
                }
            }

            ItemState.Error -> {
                Row(
                    modifier = Modifier.padding(top = 25.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = stringResource(Res.string.error),
                        tint = Theme.colorScheme.shadeSecondary,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onDelete() }
                    )
                    Icon(
                        painter = painterResource(Res.drawable.arrow_reload_horizontal),
                        contentDescription = stringResource(Res.string.retry),
                        tint = Theme.colorScheme.shadeSecondary,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onRetry() }
                    )
                }
            }

            ItemState.Success -> {
                Row(
                    modifier = Modifier.padding(top = 25.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = stringResource(Res.string.success),
                        tint = Theme.colorScheme.shadeSecondary,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onDelete() }
                    )
                }
            }
        }
    }
}

enum class ItemState {
    Loading, Error, Success
}