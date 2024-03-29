package com.mobiletraderv.paul.daggertraining


import com.jakewharton.threetenabp.AndroidThreeTen
import com.mobiletraderv.paul.daggertraining.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class BaseApplication : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        AndroidThreeTen.init(this)
        return DaggerAppComponent.builder().application(this).build()

    }
}
