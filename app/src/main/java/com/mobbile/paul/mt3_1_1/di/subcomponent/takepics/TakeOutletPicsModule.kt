package com.mobbile.paul.mt3_1_1.di.subcomponent.takepics

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.customers.pictures.TakeOutletPictureViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class TakeOutletPicsModule {
    @Binds
    @IntoMap
    @ViewModelKey(TakeOutletPictureViewModel::class)
    abstract fun bindTakeOutletPictureViewModel(viewModel: TakeOutletPictureViewModel): ViewModel
}