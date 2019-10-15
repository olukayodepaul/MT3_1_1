package com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales_history.SalesHistoryFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class Saleshistory_Module {

    @Binds
    @IntoMap
    @ViewModelKey(SalesHistoryFragmentViewModel::class)
    abstract fun bindsaleshistory_Module(viewModel: SalesHistoryFragmentViewModel): ViewModel

}