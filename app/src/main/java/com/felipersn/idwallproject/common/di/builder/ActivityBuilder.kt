package com.felipersn.idwallproject.common.di.builder

import dagger.Module

/**
 * This Class is Responsible to create a instance for each Activity in the System that need a @Inject.
 * Each Activity needs to have AndroidInjection.inject(this); in or onCreate(Bundle icicle) method.
 * To create a new one please follow the pattern.
 */
@Module
abstract class ActivityBuilder {

//    @ActivityScope
//    @ContributesAndroidInjector
//    abstract fun provideMainActivity(): LoginActivity

    //Add more Activity here


}