package net.thechance.mena.identity.data.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module
import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.singleOf
actual val IdentityPlatformModule = module {
    single { Darwin.create() }
}
