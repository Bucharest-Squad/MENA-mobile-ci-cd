package net.thechance.mena.identity.presentation.util

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import net.thechance.mena.identity.presentation.util.permissionHandler.PermissionController
import net.thechance.mena.identity.presentation.util.permissionHandler.PermissionState

internal class LocationForegroundPermission(
    private val context: Context,
    private val permissionManager: PermissionManager,
    ) : PermissionController {

        private val activity: ComponentActivity = permissionManager.getActivity() ?: throw Exception()

    override fun getPermissionState(): PermissionState {
        return checkPermissions(activity, fineLocationPermissions)
    }



    override fun openSettingPage() {
        context.openAppSettingsPage()
    }

    override fun providePermission() {
    }
}

internal val fineLocationPermissions: List<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    } else {
        listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }
