package com.mobbile.paul.mt3_1_1.di.subcomponent.salesentries

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries.SalesEntriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class SalesEntriesModule {
    @Binds
    @IntoMap
    @ViewModelKey(SalesEntriesViewModel::class)
    abstract fun bindAuthViewModel(viewModel: SalesEntriesViewModel): ViewModel
}

