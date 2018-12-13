package com.felipersn.idwallproject.common.di.builder

import androidx.lifecycle.ViewModelProvider
import com.felipersn.idwallproject.common.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilder {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    //Add more ViewModels here.


}