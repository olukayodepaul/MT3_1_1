package com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_fragment.SalesFragment_ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class Sales_Module {

    @Binds
    @IntoMap
    @ViewModelKey(SalesFragment_ViewModel::class)
    abstract fun bindsales_Module(viewModel: SalesFragment_ViewModel): ViewModel

}