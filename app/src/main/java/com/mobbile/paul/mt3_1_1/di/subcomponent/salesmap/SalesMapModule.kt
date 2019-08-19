package com.mobbile.paul.mt3_1_1.di.subcomponent.salesmap

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager.Sales_Map_Manager_ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SalesMapModule {
    @Binds
    @IntoMap
    @ViewModelKey(Sales_Map_Manager_ViewModel::class)
    abstract fun bindASalesMapViewModel(viewModel: Sales_Map_Manager_ViewModel): ViewModel
}