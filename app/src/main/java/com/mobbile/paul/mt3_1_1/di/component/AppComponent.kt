package com.mobiletraderv.paul.daggertraining.di

import android.app.Application
import com.example.kotlin_project.di.modules.*
import com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders.Sales_Module
import com.mobbile.paul.mt3_1_1.di.subcomponent.fragmentbuilders.Saleshistory_Module
import com.mobiletraderv.paul.daggertraining.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilderModule::class,
    ViewModelFactoryModule::class,
    NetworkModule::class,
    LocalDatabaseModule::class,
    AppApi::class,
    MapApi::class,
    MapNetworkModule::class
])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}
