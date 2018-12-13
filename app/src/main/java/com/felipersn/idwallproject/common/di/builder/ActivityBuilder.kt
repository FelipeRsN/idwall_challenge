package com.felipersn.idwallproject.common.di.builder

import com.felipersn.idwallproject.common.di.annotation.ActivityScope
import com.felipersn.idwallproject.presentation.ui.login.LoginActivity
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
    abstract fun provideLoginActivity(): LoginActivity

    //Add more Activity here


}