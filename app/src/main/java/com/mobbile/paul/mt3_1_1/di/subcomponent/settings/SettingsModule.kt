package com.mobbile.paul.mt3_1_1.di.subcomponent.settings

import androidx.lifecycle.ViewModel
import com.mobbile.paul.mt3_1_1.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.settings.SettingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindSettingViewModel(viewModel: SettingViewModel): ViewModel
}