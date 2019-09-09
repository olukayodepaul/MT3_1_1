package com.example.kotlin_project.di.main

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales_history.salesdetails.SalesDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class SalesDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SalesDetailsViewModel::class)
    abstract fun bindSalesDetailsViewModel(viewModel: SalesDetailsViewModel): ViewModel
}