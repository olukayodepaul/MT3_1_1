package com.mobbile.paul.mt3_1_1.di.subcomponent.customers

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.customers.CustomerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class CustomersModule {
    @Binds
    @IntoMap
    @ViewModelKey(CustomerViewModel::class)
    abstract fun bindCustomerViewModel(viewModel: CustomerViewModel): ViewModel
}

