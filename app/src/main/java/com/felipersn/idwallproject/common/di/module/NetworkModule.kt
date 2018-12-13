package com.felipersn.idwallproject.common.di.module

import android.app.Application
import android.content.Context
import com.felipersn.idwallproject.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val CACHE_SIZE_10_MB = 10 * 1024 * 1024
        private const val TIMEOUT: Long = 10
        private const val MAX_REQUESTS_PER_HOST: Int = 3
        private const val CHILD_PATH: String = "responses"
    }

    @Provides
    @Singleton
    internal fun providesOkHttpCache(application: Application): Cache {
        val cacheSize = CACHE_SIZE_10_MB
        val httpCacheDirectory = File(application.cacheDir, CHILD_PATH)
        return Cache(httpCacheDirectory, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    internal fun providesOkHttpClient(cache: Cache, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        val dispatcher = Dispatcher()
        dispatcher.maxRequestsPerHost = MAX_REQUESTS_PER_HOST

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .dispatcher(dispatcher)
            .cache(cache)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    internal fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    internal fun providesRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    internal fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        @Named(SettingsModule.BASE_URL) baseUrl: String
    ): Retrofit {


        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }
}