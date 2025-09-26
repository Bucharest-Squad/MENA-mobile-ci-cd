package net.thechance.mena.dukan.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.image.Image
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImageWithTextContainer(
    blurImageRes: DrawableResource,
    foregroundImageRes: DrawableResource,
    header: @Composable () -> Unit,
    bodyText: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(Theme.spacing._24)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(blurImageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .blur(
                            radius = 30.dp,
                            edgeTreatment = BlurredEdgeTreatment.Unbounded
                        )
                        .offset(y = 20.dp)
                        .align(Alignment.BottomCenter)
                )
                Image(
                    painter = painterResource(foregroundImageRes),
                    contentDescription = "Foreground image resource"
                )
            }

            Box(modifier = Modifier.padding(top = Theme.spacing._12)) {
                header()
            }

            Text(
                text = bodyText,
                style = Theme.typography.body.small,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(Theme.spacing._2)
            )
        }
    }
}