package com.felipersn.idwallproject.common.di.module

import com.felipersn.idwallproject.data.store.remote.services.LoginService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkServiceModule {

    @Provides
    @Singleton
    fun providesLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    //Add more Retrofit services here.

}