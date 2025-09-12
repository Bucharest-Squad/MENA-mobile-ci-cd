package net.thechance.mena.core_chat.presentation.di

import net.thechance.mena.core_chat.api.CoreChatApi
import net.thechance.mena.core_chat.presentation.api.CoreChatApiImp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val ApiModule = module {
    singleOf(::CoreChatApiImp) bind CoreChatApi::class
}