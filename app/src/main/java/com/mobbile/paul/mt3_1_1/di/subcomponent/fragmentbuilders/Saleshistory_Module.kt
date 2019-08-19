package com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales_history.SalesHistoryFragment_ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class Saleshistory_Module {

    @Binds
    @IntoMap
    @ViewModelKey(SalesHistoryFragment_ViewModel::class)
    abstract fun bindsaleshistory_Module(viewModel: SalesHistoryFragment_ViewModel): ViewModel

}