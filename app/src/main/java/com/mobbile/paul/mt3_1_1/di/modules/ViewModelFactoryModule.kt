package com.mobbile.paul.mt3_1_1.di.modules



import androidx.lifecycle.ViewModelProvider
import com.mobbile.paul.mt3_1_1.viewmodel.ViewModeProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactoryModule: ViewModeProviderFactory): ViewModelProvider.Factory
}
