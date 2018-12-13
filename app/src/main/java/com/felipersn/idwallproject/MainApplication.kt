package com.felipersn.idwallproject

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.felipersn.idwallproject.common.di.DaggerAppComponent
import com.felipersn.idwallproject.common.di.module.ApplicationModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate() {
        super.onCreate()
        init()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    private fun init() {
        initDagger()
        initConfigs()
    }

    private fun initConfigs() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun initDagger() {
        DaggerAppComponent
            .builder()
            .application(this)
            .applicationModule(ApplicationModule(this))
            .build()
            .inject(this)
    }
}