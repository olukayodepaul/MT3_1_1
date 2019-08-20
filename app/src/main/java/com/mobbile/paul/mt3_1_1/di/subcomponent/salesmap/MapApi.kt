package com.example.kotlin_project.di.modules


import com.mobbile.paul.mt3_1_1.providers.MAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class MapApi {

    @Singleton
    @Provides
    internal fun provideMapApi(@Named("map_api") retrofit: Retrofit): MAPI {
        return retrofit.create(MAPI::class.java)
    }

}