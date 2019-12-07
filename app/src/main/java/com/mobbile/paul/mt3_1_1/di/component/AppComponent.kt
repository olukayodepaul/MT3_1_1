package com.mobbile.paul.mt3_1_1.di.component

import android.app.Application
import com.mobbile.paul.mt3_1_1.BaseApplication
import com.mobbile.paul.mt3_1_1.di.modules.*
import com.mobbile.paul.mt3_1_1.di.subcomponent.salesmap.MapNetworkModule
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
    AppApi::class,
    NetworkModule::class,
    MapGoogleApi::class,
    MapNetworkModule::class,
    LocalDatabaseModule::class,
    NodejsModules::class,
    NodejsNetworkModule::class
])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}
