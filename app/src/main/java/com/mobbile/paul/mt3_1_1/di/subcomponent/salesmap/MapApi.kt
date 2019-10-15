package com.mobbile.paul.mt3_1_1.di.subcomponent.salesmap


import com.mobbile.paul.mt3_1_1.providers.MapApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class MapApi {

    @Singleton
    @Provides
    internal fun provideMapApi(@Named("map_api") retrofit: Retrofit): MapApi {
        return retrofit.create(MapApi::class.java)
    }

}