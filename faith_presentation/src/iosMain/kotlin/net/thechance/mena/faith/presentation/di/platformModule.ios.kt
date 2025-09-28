package net.thechance.mena.faith.presentation.di

import net.thechance.mena.faith.presentation.util.ClipboardManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    singleOf(::ClipboardManager)
}
