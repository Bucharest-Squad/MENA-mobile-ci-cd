package net.thechance.mena.mange_my_trends


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mena.trends_presentation.generated.resources.Res
import mena.trends_presentation.generated.resources.ic_arrow_left
import mena.trends_presentation.generated.resources.ic_delete
import mena.trends_presentation.generated.resources.ic_eye
import mena.trends_presentation.generated.resources.ic_like
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
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
    Box(modifier = Modifier.fillMaxSize()) {
        RunningVideoPlaceHolder()
        UsersReAct(modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp))

        //TODO pass listener and ui state to replace this dummy data
        PublisherDetails(
            userName = "Hawraa Mahmood",
            timeOfPublish = "2 hour ago",
            description = "Latest AI -trends that are changing everything!",
            modifier =  Modifier.align(Alignment.BottomCenter)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 118.dp)
                .gradientShadow(startColor = Color(0x33FFFFFF), endColor = Color(0x00FFFFFF))
                .align(Alignment.TopCenter)
        ) {
            AppBar(
                title = "", modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                leadingContent = {
                    MenaIcon(painter = painterResource(resource = Res.drawable.ic_arrow_left))
                },
                onLeadingClick = { TODO("on back click") }
            )
        }

        Box(modifier = Modifier.fillMaxWidth().height(height = 118.dp).gradientShadow().align(Alignment.BottomCenter))
    }
}

@Composable
private fun PublisherDetails(
    userName: String,
    timeOfPublish: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().navigationBarsPadding()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Theme.spacing._8),
            modifier = Modifier.padding(horizontal = Theme.spacing._16).padding(top = Theme.spacing._8)
        ) {
            ProfilePlaceHolder()
            Column(Modifier.padding(bottom = Theme.spacing._16)) {
                MenaText(
                    text = userName,
                    color = Theme.colorScheme.primary.onPrimary,
                    style = Theme.typography.label.medium,
                    modifier = Modifier.padding(vertical = Theme.spacing._2)
                )
                MenaText(
                    text = timeOfPublish, color = Theme.colorScheme.shadeTertiary,
                    style = Theme.typography.label.small
                )
            }
        }
        MenaText(
            text = description,
            modifier = Modifier.padding(bottom = Theme.spacing._32).padding(horizontal = Theme.spacing._16),
            color = Theme.colorScheme.primary.onPrimary,
            maxLines = 1,
            style = Theme.typography.label.medium
        )
    }
}

@Composable
private fun UsersReAct(modifier: Modifier= Modifier) {
    Column(modifier) {
        ReActIcon(iconRes = painterResource(resource = Res.drawable.ic_like),reactCount ="1",modifier = Modifier.padding(bottom = 24.dp))
        ReActIcon(iconRes = painterResource(resource = Res.drawable.ic_eye),reactCount ="11",modifier = Modifier.padding(bottom = 24.dp) )
        ReActIcon(iconRes = painterResource(resource = Res.drawable.ic_delete),reactCount ="Delete",modifier = Modifier.padding(bottom = 24.dp), onClick = {TODO()})
    }
}

@Composable
private fun ProfilePlaceHolder(){
    Box(Modifier.background(shape = CircleShape, color = Color.Cyan).size(size = 40.dp))
}

@Composable
private fun RunningVideoPlaceHolder() {
    Box(Modifier.background(Color.Black).fillMaxSize())
}

@Composable
private fun ReActIcon(
    iconRes: Painter,
    reactCount: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        MenaIcon(painter = iconRes,modifier = Modifier.padding(bottom = 8.dp).clickable{onClick()})
        MenaText(
            text = reactCount,
            style = Theme.typography.label.small,
            color = Theme.colorScheme.shadeTertiary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun DisplayingMyTrendPreview(){
    DisplayingMyTrend()
}