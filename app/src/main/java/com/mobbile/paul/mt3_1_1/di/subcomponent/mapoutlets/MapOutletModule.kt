package com.mobbile.paul.mt3_1_1.di.subcomponent.mapoutlets

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.customers.newcustomers.MapOutletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class MapOutletModule {
    @Binds
    @IntoMap
    @ViewModelKey(MapOutletViewModel::class)
    abstract fun bindMapOutletViewModelModel(viewModel: MapOutletViewModel): ViewModel
}