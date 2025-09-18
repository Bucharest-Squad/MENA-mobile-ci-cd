package net.thechance.mena

import net.thechance.mena.identity.domain.di.domainModule
import net.thechance.mena.identity.presentation.di.identityScreensModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        val identityModules = listOf(
            identityScreensModule,
            domainModule
        )

        modules(
            modules = identityModules // todo  + chatModules + dukanModules
        )
    }
}