package com.example.kotlin_project.di.main

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
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