package com.example.kotlin_project.di.modules

import com.example.kotlin_project.providers.Api
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppApi {

    @Singleton
    @Provides
    internal fun provideMainApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

}