package net.thechance.mena.identity.data.di

import net.thechance.mena.identity.data.dataSource.local.memory.ImageCacheManager
import org.koin.dsl.module

actual val IdentityPlatformModule = module {
        single { ImageCacheManager() }
}

