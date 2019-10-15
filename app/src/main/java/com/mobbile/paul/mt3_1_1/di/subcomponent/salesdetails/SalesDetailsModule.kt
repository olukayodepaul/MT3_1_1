package com.mobbile.paul.mt3_1_1.di.subcomponent.salesdetails

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
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