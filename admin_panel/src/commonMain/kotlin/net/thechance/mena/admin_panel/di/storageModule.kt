package net.thechance.mena.admin_panel.di

import com.russhwolf.settings.Settings
import org.koin.dsl.module

val storageModule = module {
    single { Settings() }
}