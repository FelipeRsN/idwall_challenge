package com.felipersn.idwallproject.common.di.builder

import com.felipersn.idwallproject.common.di.annotation.ActivityScope
import com.felipersn.idwallproject.presentation.ui.login.LoginActivity
import com.felipersn.idwallproject.presentation.ui.mainlist.MainListActivity
import com.felipersn.idwallproject.presentation.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This Class is Responsible to create a instance for each Activity in the System that need a @Inject.
 * Each Activity needs to have AndroidInjection.inject(this); in or onCreate(Bundle icicle) method.
 * To create a new one please follow the pattern.
 */
@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideSplashActivity(): SplashActivity

    //Add more Activity here

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideMainListActivity(): MainListActivity

}