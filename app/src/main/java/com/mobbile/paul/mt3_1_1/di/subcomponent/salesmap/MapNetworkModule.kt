package com.mobbile.paul.mt3_1_1.di.subcomponent.salesmap


import android.app.Application
import com.mobbile.paul.mt3_1_1.BuildConfig
import com.mobbile.paul.mt3_1_1.util.ConnectivityInterceptorImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@Suppress("unused")
class MapNetworkModule {

    @Provides
    @Singleton
    @Named("map_api")
    internal fun provideNetworkInstance(application: Application): Retrofit {

        val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            okHttpClientBuilder
                .addInterceptor(ConnectivityInterceptorImpl(application))
                .addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com")
            .client(okHttpClientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
       }
}
