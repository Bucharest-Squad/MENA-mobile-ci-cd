package net.thechance.mena.identity.presentation.util

import android.content.Context
import androidx.activity.ComponentActivity
import net.thechance.mena.identity.presentation.util.permissionHandler.PermissionController
import net.thechance.mena.identity.presentation.util.permissionHandler.PermissionState

class CameraPermission(
    private val permissionManager: PermissionManager,
    private val context: Context,
    ) : PermissionController {

    private var activity: ComponentActivity = permissionManager.getActivity() ?: throw Exception()

    override fun getPermissionState(): PermissionState {
        if (activity.hasCameraPermission()) return PermissionState.GRANTED
        return PermissionState.DENIED
    }

    override fun openSettingPage() {
        context.openAppSettingsPage()
    }

    override fun providePermission() {

    }


}