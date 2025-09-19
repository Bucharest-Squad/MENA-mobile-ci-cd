package net.thechance.mena.faith.presentation.di

import net.thechance.mena.faith.presentation.feature.quran.sur.SurViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { SurViewModel(get()) }
}