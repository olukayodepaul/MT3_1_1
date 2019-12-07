package com.mobbile.paul.mt3_1_1.di.modules


import android.app.Application
import com.mobbile.paul.mt3_1_1.BuildConfig
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
class NodejsNetworkModule {

    @Provides
    @Singleton
    @Named("nodejs_network")
    internal fun provideRetrofitInstance(application: Application): Retrofit {

        val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(2*30, TimeUnit.SECONDS)
            .writeTimeout(2*30, TimeUnit.SECONDS)
            .connectTimeout(2*30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            okHttpClientBuilder
                //.addInterceptor(ConnectivityInterceptorImpl(application))
                .addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl("http://mtnodejsapi.com")
            .client(okHttpClientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
       }
}
