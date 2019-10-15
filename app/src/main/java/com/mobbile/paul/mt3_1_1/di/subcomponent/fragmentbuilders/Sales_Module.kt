package com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_fragment.SalesFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class Sales_Module {

    @Binds
    @IntoMap
    @ViewModelKey(SalesFragmentViewModel::class)
    abstract fun bindsales_Module(viewModel: SalesFragmentViewModel): ViewModel

}