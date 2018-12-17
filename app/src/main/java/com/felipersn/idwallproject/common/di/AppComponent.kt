package com.felipersn.idwallproject.common.di

import com.felipersn.idwallproject.MainApplication
import com.felipersn.idwallproject.common.di.builder.ActivityBuilder
import com.felipersn.idwallproject.common.di.builder.ViewModelBuilder
import com.felipersn.idwallproject.common.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityBuilder::class,
        ViewModelBuilder::class,
        NetworkModule::class,
        NetworkServiceModule::class,
        SettingsModule::class,
        GsonModule::class,
        SharedPreferenceModule::class
    ]
)
interface AppComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MainApplication): Builder

        fun applicationModule(applicationModule: ApplicationModule): Builder
        fun build(): AppComponent
    }

}