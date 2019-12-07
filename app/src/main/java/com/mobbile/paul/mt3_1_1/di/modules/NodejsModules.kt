package com.mobbile.paul.mt3_1_1.di.modules

import com.mobbile.paul.mt3_1_1.providers.NodejsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NodejsModules {

    @Singleton
    @Provides
    internal fun provideNodejsApi(@Named("nodejs_network") retrofit: Retrofit):  NodejsApi{
        return retrofit.create(NodejsApi::class.java)
    }

}