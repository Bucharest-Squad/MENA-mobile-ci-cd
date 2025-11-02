package net.thechance.mena.core_chat.data.di

import net.thechance.mena.core_chat.data.utils.FileManager
import org.koin.dsl.module

val fileManagerAndroidModule = module {
    single {
        FileManager()
    }
}