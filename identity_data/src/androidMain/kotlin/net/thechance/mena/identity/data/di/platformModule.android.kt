package net.thechance.mena.identity.data.di

import com.russhwolf.settings.Settings
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::Settings)
    singleOf(CIO.create)
}