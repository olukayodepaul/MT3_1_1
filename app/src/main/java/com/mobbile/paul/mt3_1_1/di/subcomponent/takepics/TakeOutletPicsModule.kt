package com.example.kotlin_project.di.main

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
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