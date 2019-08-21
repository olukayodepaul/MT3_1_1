package com.example.kotlin_project.di.modules

import com.example.kotlin_project.di.main.*
import com.mobbile.paul.mt3_1_1.di.subcomponent.attendant.AttendantModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders.FragmentBuilder
import com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders.Sales_Module
import com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders.Saleshistory_Module
import com.mobbile.paul.mt3_1_1.di.subcomponent.salesmap.SalesMapModule
import com.mobbile.paul.mt3_1_1.ui.auth.AuthActivity
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesActivity
import com.mobbile.paul.mt3_1_1.ui.sales.SalesViewpager
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant.Sales_Attendant
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager.UsersMap
import com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries.SalesEntries
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilderModule {

    @ModulesScope
    @ContributesAndroidInjector(
        modules = [
            ModulesModule::class
        ]
    )
    abstract fun contributeActivityAndroidInjector(): ModulesActivity

    @AuthScope
    @ContributesAndroidInjector(
        modules = [
            AuthModule::class
        ]
    )
    abstract fun contributeAuthViewModelModuleAndroidInjector(): AuthActivity


    @FragmentBuilderScope
    @ContributesAndroidInjector(modules = [
        FragmentBuilder::class,
        Sales_Module::class,
        Saleshistory_Module::class
    ])
    abstract fun contributeSalesPagerActivity(): SalesViewpager


    @AttendantScope
    @ContributesAndroidInjector(modules = [
        AttendantModule::class
    ])
    abstract fun contributeAttendantActivity(): Sales_Attendant

    @SalesMapScope
    @ContributesAndroidInjector(modules = [
        SalesMapModule::class
    ])
    abstract fun contributeSalesMapActivity(): UsersMap


    @SalesEntriesScope
    @ContributesAndroidInjector(modules = [
        SalesEntriesModule::class
    ])
    abstract fun contributeSalesEntriesActivity(): SalesEntries

}