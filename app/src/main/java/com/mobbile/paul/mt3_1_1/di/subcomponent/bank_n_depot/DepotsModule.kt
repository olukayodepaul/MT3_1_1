package com.example.kotlin_project.di.main

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales.depots.DepotViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class DepotsModule {
    @Binds
    @IntoMap
    @ViewModelKey(DepotViewModel::class)
    abstract fun bindDepotViewModel(viewModel: DepotViewModel): ViewModel
}