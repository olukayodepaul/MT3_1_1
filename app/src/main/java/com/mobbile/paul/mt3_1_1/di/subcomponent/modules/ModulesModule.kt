package com.mobbile.paul.mt3_1_1.di.subcomponent.modules

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class ModulesModule {
    @Binds
    @IntoMap
    @ViewModelKey(ModulesViewModel::class)
    abstract fun bindAuthViewModel(viewModel: ModulesViewModel): ViewModel
}