package net.thechance.mena

import net.thechance.mena.identity.data.di.platformModule
import net.thechance.mena.identity.data.di.sharedModule
import net.thechance.mena.identity.presentation.di.identityScreensModule
import net.thechance.mena.identity.data.di.platformModule
import net.thechance.mena.identity.data.di.sharedModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        val identityModules = listOf(
            identityScreensModule,
            platformModule,
            sharedModule,
        )

        modules(
            modules = identityModules // todo  + chatModules + dukanModules
        )
    }
}