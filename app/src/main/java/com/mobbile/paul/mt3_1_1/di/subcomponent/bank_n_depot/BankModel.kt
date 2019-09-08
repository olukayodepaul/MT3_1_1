package com.mobbile.paul.mt3_1_1.di.subcomponent.bank_n_depot

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales.bank.BankViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class BankModel {
    @Binds
    @IntoMap
    @ViewModelKey(BankViewModel::class)
    abstract fun bindBankViewModel(viewModel: BankViewModel): ViewModel
}


