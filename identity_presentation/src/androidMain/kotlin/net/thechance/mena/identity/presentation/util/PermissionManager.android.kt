package net.thechance.mena.identity.presentation.util

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.ref.WeakReference
import kotlin.coroutines.resume

class PermissionManager() {
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


    fun requestPermission(permission: String, onResult: (Boolean) -> Unit) {
        Companion.onResult = onResult
        launcher.launch(permission)
    }

    fun getActivity(): ComponentActivity? {
        return activityRef?.get()
    }
}
