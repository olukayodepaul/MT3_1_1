package com.mobbile.paul.mt3_1_1.di.subcomponent.attendant

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.di.modules.ViewModelKey
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant.SalesAttendantViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AttendantModule {
    @Binds
    @IntoMap
    @ViewModelKey(SalesAttendantViewModel::class)
    abstract fun bindAttendantViewModel(viewModel: SalesAttendantViewModel): ViewModel
}