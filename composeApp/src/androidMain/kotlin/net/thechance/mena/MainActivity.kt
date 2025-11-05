package net.thechance.mena

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.init
import net.thechance.mena.appEntryPoint.DeepLink
import net.thechance.mena.identity.presentation.util.PermissionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        PermissionManager.init(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FileKit.init(this)
        val deepLink = parseDeepLinkFromIntent()
        setContent {
            App(deepLink)
        }
    }

    private fun parseDeepLinkFromIntent(): DeepLink {
        val appLinkData: Uri? = intent.data
        return DeepLink(
            userId = appLinkData?.getQueryParameter("userId"),
            userName = appLinkData?.getQueryParameter("userName"),
        )
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(deepLink = DeepLink(null, null))
}