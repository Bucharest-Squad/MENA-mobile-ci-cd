package net.thechance.mena.identity.presentation.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import net.thechance.mena.identity.domain.exception.CannotOpenSettingsException
import androidx.core.net.toUri
import net.thechance.mena.identity.presentation.util.permissionHandler.PermissionState

internal fun Context.openPage(
    action: String,
    newData: Uri? = null,
    onError: (Exception) -> Unit,
) {
    try {
        val intent = Intent(action).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            newData?.let { data = it }
        }
        startActivity(intent)
    } catch (e: Exception) {
        onError(e)
    }
}

internal fun Context.openAppSettingsPage() {
    openPage(
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        newData = "package:$packageName".toUri(),
        onError = { throw CannotOpenSettingsException() }
    )
}

internal fun Context.hasCameraPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

internal fun checkPermissions(
    activity: Activity,
    permissions: List<String>
): PermissionState {
    if (permissions.isEmpty()) return PermissionState.GRANTED

    val notGranted = permissions.filter {
        ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
    }

    if (notGranted.isEmpty()) return PermissionState.GRANTED

    val shouldShowRationale = notGranted.any {
        activity.shouldShowRequestPermissionRationale(it)
    }

    return when {
        shouldShowRationale -> PermissionState.DENIED
        else -> PermissionState.NOT_DETERMINED
    }
}
