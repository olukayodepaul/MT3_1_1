package com.mobbile.paul.mt3_1_1.di.modules


import com.example.kotlin_project.di.main.*
import com.mobbile.paul.mt3_1_1.di.subcomponent.attendant.AttendantModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.auth.AuthModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.bank_n_depot.BankModel
import com.mobbile.paul.mt3_1_1.di.subcomponent.bank_n_depot.DepotsModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.customers.CustomersModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders.FragmentBuilder
import com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders.SalesComissionModel
import com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders.Sales_Module
import com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders.Saleshistory_Module
import com.mobbile.paul.mt3_1_1.di.subcomponent.modules.ModulesModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.order.OrderModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.salesdetails.SalesDetailsModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.salesentries.SalesEntriesModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.salesmap.SalesMapModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.salesmap.SalesMapScope
import com.mobbile.paul.mt3_1_1.di.subcomponent.settings.SettingsModule
import com.mobbile.paul.mt3_1_1.di.subcomponent.takepics.TakeOutletPicsModule
import com.mobbile.paul.mt3_1_1.ui.auth.AuthActivity
import com.mobbile.paul.mt3_1_1.ui.customers.CustomerPageViwer
import com.mobbile.paul.mt3_1_1.ui.customers.editcustomer.EditCustomerActivity
import com.mobbile.paul.mt3_1_1.ui.customers.pictures.TakeOutletPicture
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesActivity
import com.mobbile.paul.mt3_1_1.ui.sales.SalesViewpager
import com.mobbile.paul.mt3_1_1.ui.sales.sales.bank.BankActivity
import com.mobbile.paul.mt3_1_1.ui.sales.sales.depots.DepotsActivity
import com.mobbile.paul.mt3_1_1.ui.sales.sales.orderedsku.OrderedSku
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_attendant.SalesAttendant
import com.mobbile.paul.mt3_1_1.ui.sales.sales.sales_map_manager.UsersMap
import com.mobbile.paul.mt3_1_1.ui.sales.sales.salesentries.SalesEntries
import com.mobbile.paul.mt3_1_1.ui.sales.sales_history.salesdetails.SalesDetailsActivity
import com.mobbile.paul.mt3_1_1.ui.settings.SettingsActivity
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
        Saleshistory_Module::class,
        SalesComissionModel::class
    ])
    abstract fun contributeSalesPagerActivity(): SalesViewpager


    @AttendantScope
    @ContributesAndroidInjector(modules = [
        AttendantModule::class
    ])
    abstract fun contributeAttendantActivity(): SalesAttendant

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

    @OrderScope
    @ContributesAndroidInjector(modules = [
        OrderModule::class
    ])
    abstract fun contributeOrderActivity(): OrderedSku

    @TakeOutletPicsScope
    @ContributesAndroidInjector(modules = [
        TakeOutletPicsModule::class
    ])
    abstract fun contributeTakeOutletPicsActivity(): TakeOutletPicture


    @BankScope
    @ContributesAndroidInjector(modules = [
        BankModel::class
    ])
    abstract fun contributeBankModel(): BankActivity


    @DepotsScope
    @ContributesAndroidInjector(modules = [
        DepotsModule::class
    ])
    abstract fun contributeDepotsModule(): DepotsActivity


    @SalesDetailsScope
    @ContributesAndroidInjector(
        modules = [
            SalesDetailsModule::class
        ]
    )
    abstract fun contributeSalesDetailsModuleAndroidInjector(): SalesDetailsActivity

    @CustomersScope
    @ContributesAndroidInjector(
        modules = [
            CustomersModule::class
        ]
    )
    abstract fun contributeCustomersModuleAndroidInjector(): CustomerPageViwer

    @CustomersScope
    @ContributesAndroidInjector(
        modules = [
            CustomersModule::class
        ]
    )
    abstract fun contributeEditCustomerActivityAndroidInjector(): EditCustomerActivity

    @SettingsScope
    @ContributesAndroidInjector(
        modules = [
            SettingsModule::class
        ]
    )
    abstract fun contributeSettingsModuleCustAndroidInjector(): SettingsActivity

}