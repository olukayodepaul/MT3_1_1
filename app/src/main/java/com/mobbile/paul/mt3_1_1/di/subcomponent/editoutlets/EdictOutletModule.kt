package com.mobbile.paul.mt3_1_1.di.subcomponent.editoutlets

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.customers.editcustomer.EditCustomerViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class EdictOutletModule {
    @Binds
    @IntoMap
    @ViewModelKey(EditCustomerViewModel::class)
    abstract fun bindEditCustomerViewModel(viewModel: EditCustomerViewModel): ViewModel
}