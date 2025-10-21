package net.thechance.mena.identity.presentation.util

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.ref.WeakReference
import kotlin.coroutines.resume

actual class PermissionManager() {
    companion object {
        private var activityRef: WeakReference<ComponentActivity>? = null

        lateinit var launcher: ActivityResultLauncher<String>

        lateinit var onResult: (Boolean) -> Unit

        fun init(activity: ComponentActivity) {
            activityRef = WeakReference(activity)

            launcher = activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                onResult(isGranted)
            }
        }
    }

    fun getActivity(): ComponentActivity? {
        return activityRef?.get()
    }

    actual suspend fun requestCameraPermission(
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        val activity = activityRef?.get()
            ?: throw IllegalStateException("PermissionManager not initialized. Call PermissionManager.init(activity) from an Activity.")

        suspendCancellableCoroutine { continuation ->
            when {
                activity.hasCameraPermission() -> {
                    onGranted()
                    continuation.resume(Unit)
                }

                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.CAMERA
                ) -> {
                    activity.openAppSettingsPage()
                    continuation.resume(Unit)
                }

                else -> {
                    launcher.launch(Manifest.permission.CAMERA)

                    onResult = { isGranted ->
                        if (isGranted) {
                            onGranted()
                        } else {
                            onDenied()
                        }
                        continuation.resume(Unit)
                    }
                }
            }
        }
    }
}
