package net.thechance.mena.trends.presentation.screen.mange_my_trends


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mena.trends_presentation.generated.resources.Res
import mena.trends_presentation.generated.resources.ic_arrow_left
import mena.trends_presentation.generated.resources.ic_delete
import mena.trends_presentation.generated.resources.ic_eye
import mena.trends_presentation.generated.resources.ic_like
import mena.trends_presentation.generated.resources.test
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.component.image.MenaImage
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.trends.presentation.shared.util.gradientShadow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DisplayingMyTrend() {
    //TODO pass listener and ui state to replace this dummy data
    DisplayingMyTrendContent()
}

@Composable
private fun DisplayingMyTrendContent() {
    Box(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {

        RunningVideoPlaceHolder()
        TopAppBar(onBackClick = { TODO("call on back click")})

        // TODO replace this dummy data with real one
        UsersReAct(
            viewCount = "11",
            likeCount = "4",
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(end = Theme.spacing._16, bottom = 140.dp)
        )

        PublisherDetails(
            userName = "Hawraa Mahmood",
            timeOfPublish = "2 hour ago",
            description = "Latest AI -trends that are changing everything! \uD83D\uDE80",
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Box(
            modifier = Modifier.fillMaxWidth().height(height = 118.dp).gradientShadow()
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 96.dp)
            .gradientShadow(startColor = Color(0x33FFFFFF), endColor = Color(0x00FFFFFF))
    ) {
        AppBar(
            title = "",
            contentPadding = PaddingValues(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + Theme.spacing._16,
                start = Theme.spacing._16
            ),
            leadingContent = {
                MenaIcon(painter = painterResource(resource = Res.drawable.ic_arrow_left))
            },
            onLeadingClick = { onBackClick() }
        )
    }
}

@Composable
private fun PublisherDetails(
    userName: String,
    timeOfPublish: String,
    description: String,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Theme.spacing._8),
            modifier = Modifier
                .padding(horizontal = Theme.spacing._16)
                .padding(top = Theme.spacing._8)
        ) {
            AvatarPlaceHolder()

            Column(Modifier.padding(bottom = Theme.spacing._16)) {
                MenaText(
                    text = userName,
                    color = Theme.colorScheme.primary.onPrimary,
                    style = Theme.typography.label.medium,
                    modifier = Modifier.padding(vertical = Theme.spacing._2)
                )

                MenaText(
                    text = timeOfPublish,
                    color = Theme.colorScheme.shadeTertiary,
                    style = Theme.typography.label.small
                )
            }
        }

        MenaText(
            text = description,
            modifier = Modifier
                .padding(bottom = Theme.spacing._32)
                .padding(horizontal = Theme.spacing._16),
            color = Theme.colorScheme.primary.onPrimary,
            style = Theme.typography.label.medium,
            maxLines = if (isExpanded) Int.MAX_VALUE else 1
        )
    }
}

@Composable
private fun RunningVideoPlaceHolder() {
    Box(Modifier.background(Color.Black).fillMaxSize())
}

@Composable
private fun UsersReAct(likeCount: String, viewCount: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(bottom = Theme.spacing._24)) {
        ReActIcon(
            icon = painterResource(resource = Res.drawable.ic_like),
            reactCount = likeCount,
            modifier = Modifier.padding(bottom = Theme.spacing._24)
        )

        ReActIcon(
            icon = painterResource(resource = Res.drawable.ic_eye),
            reactCount = viewCount,
            modifier = Modifier.padding(bottom = Theme.spacing._24)
        )

        ReActIcon(
            icon = painterResource(resource = Res.drawable.ic_delete),
            reactCount = "Delete",
            onClick = { TODO() })
    }
}

@Composable
private fun ReActIcon(
    icon: Painter,
    reactCount: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        MenaIcon(painter = icon, modifier = Modifier.padding(bottom = 8.dp).clickable { onClick() })
        MenaText(
            text = reactCount,
            style = Theme.typography.label.small,
            color = Theme.colorScheme.shadeTertiary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AvatarPlaceHolder() {
    MenaImage(
        painter = painterResource(resource = Res.drawable.test),
        modifier = Modifier.size(size = 40.dp).clip(shape = CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun DisplayingMyTrendPreview() {
    DisplayingMyTrend()
}