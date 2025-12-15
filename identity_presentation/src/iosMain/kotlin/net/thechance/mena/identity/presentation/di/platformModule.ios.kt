package net.thechance.mena.identity.presentation.di

import net.thechance.mena.identity.presentation.core.util.AppLocalizer
import net.thechance.mena.identity.presentation.core.util.permissionHandler.PermissionController
import net.thechance.mena.identity.presentation.core.util.permissionHandler.Permissions
import net.thechance.mena.identity.presentation.core.util.permissions.CameraPermission
import net.thechance.mena.identity.presentation.core.util.permissions.GalleryPermission
import net.thechance.mena.identity.presentation.core.util.permissions.LocationForegroundPermission
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single<PermissionController>(named(Permissions.LOCATION_FOREGROUND.name)) {
        LocationForegroundPermission()
    }

    single<PermissionController>(named(Permissions.GALLERY_IMAGES.name)) {
        GalleryPermission()
    }

    single<PermissionController>(named(Permissions.CAMERA.name)){
        CameraPermission()
    }

    single<AppLocalizer>(
        createdAtStart = true
    ) {
        AppLocalizer(
            settingsRepository = get()
        )
    }
}