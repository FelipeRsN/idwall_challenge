package com.felipersn.idwallproject.common.di.module

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {

    companion object {
        private const val SHARED_PREFERENCE_LOGIN_ID = "SHARED_PREFERENCE_LOGIN_ID"
        const val SHARED_PREFERENCE_LOGIN_STATE = "SHARED_PREFERENCE_LOGIN_STATE"
        const val SHARED_PREFERENCE_LOGIN_TOKEN = "SHARED_PREFERENCE_LOGIN_TOKEN"
    }

    @Provides
    @Singleton
    internal fun provideSharedPreference(application: Application): SharedPreferences {
        return application.getSharedPreferences(SHARED_PREFERENCE_LOGIN_ID, MODE_PRIVATE)
    }
}