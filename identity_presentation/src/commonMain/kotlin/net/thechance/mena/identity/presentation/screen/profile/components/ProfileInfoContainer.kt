package net.thechance.mena.identity.presentation.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.ic_share
import mena.identity_presentation.generated.resources.profile_profile_picture_content_description
import mena.identity_presentation.generated.resources.profile_title
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileInfoContainer(
    profilePicture: String,
    fullName: String,
    userName: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(Theme.colorScheme.stroke
    , shape = CircleShape)
                )
         {
            AsyncImage(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxSize()
                    .clip(CircleShape)
                    ,
                model = profilePicture,
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(Res.string.profile_profile_picture_content_description),
                placeholder = painterResource(Res.drawable.ic_share),
            )
            Box(
                modifier = Modifier
                    .padding(end = 15.dp, bottom = 3.dp)
                    .align(Alignment.BottomEnd)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Theme.colorScheme.stroke)
                    .padding(1.dp)
                    .clip(CircleShape)
                    .background(Theme.colorScheme.success)
            )
        }
        Text(
            text = fullName,
            style = Theme.typography.label.medium,
            color = Theme.colorScheme.shadePrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = userName,
            style = Theme.typography.label.small,
            color = Theme.colorScheme.shadeSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Preview()
@Composable
fun PreviewProfileInfoContainer() {
    MenaTheme {
        ProfileInfoContainer(
            profilePicture = "https://images.unsplash.com/photo-1743701168206-bd617221b559?q=80&w=814&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            fullName = "Mohammed Ahmed Mansour",
            userName = "@Mohammed_2025",
        )
    }
}
