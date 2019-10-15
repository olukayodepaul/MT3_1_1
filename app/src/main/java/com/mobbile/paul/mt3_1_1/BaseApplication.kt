package com.mobbile.paul.mt3_1_1

import com.jakewharton.threetenabp.AndroidThreeTen
import com.mobbile.paul.mt3_1_1.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        AndroidThreeTen.init(this)
        return DaggerAppComponent.builder().application(this).build()
    }
}
