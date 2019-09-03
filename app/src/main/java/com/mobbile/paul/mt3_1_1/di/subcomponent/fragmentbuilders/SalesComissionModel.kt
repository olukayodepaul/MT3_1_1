package com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.commission.SalesCommissionFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class SalesComissionModel {

    @Binds
    @IntoMap
    @ViewModelKey(SalesCommissionFragmentViewModel::class)
    abstract fun bindsalesComissionModel(viewModel: SalesCommissionFragmentViewModel): ViewModel

}