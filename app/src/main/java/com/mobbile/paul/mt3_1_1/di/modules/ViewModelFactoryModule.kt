package com.example.kotlin_project.di.modules



import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_project.viewmodel.ViewModeProviderFactory
import dagger.Binds
import dagger.Module



@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactoryModule: ViewModeProviderFactory): ViewModelProvider.Factory
}