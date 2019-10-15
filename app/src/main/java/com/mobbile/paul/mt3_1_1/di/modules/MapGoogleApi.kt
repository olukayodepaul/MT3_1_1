package com.mobbile.paul.mt3_1_1.di.modules


import com.mobbile.paul.mt3_1_1.providers.MapApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class MapGoogleApi {

    @Singleton
    @Provides
    internal fun provideGooglepi(@Named("map_api") retrofit: Retrofit): MapApi {
        return retrofit.create(MapApi::class.java)
    }

}