package com.example.kotlin_project.di.modules

import com.example.kotlin_project.di.main.SalesMapScope
import com.example.kotlin_project.providers.Api
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class MapApi {

    @SalesMapScope
    @Provides
    internal fun provideMapApi(@Named("map_api") retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

}