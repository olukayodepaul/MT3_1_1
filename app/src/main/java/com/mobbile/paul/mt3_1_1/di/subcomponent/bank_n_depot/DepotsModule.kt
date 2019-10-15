package com.mobbile.paul.mt3_1_1.di.subcomponent.bank_n_depot

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales.depots.DepotViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class DepotsModule {
    @Binds
    @IntoMap
    @ViewModelKey(DepotViewModel::class)
    abstract fun bindDepotViewModel(viewModel: DepotViewModel): ViewModel
}
