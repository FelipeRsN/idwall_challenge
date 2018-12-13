package com.felipersn.idwallproject.common.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return mApplication.applicationContext
    }
}