package com.felipersn.idwallproject.common.di.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felipersn.idwallproject.common.di.annotation.ViewModelKey
import com.felipersn.idwallproject.common.di.factory.ViewModelFactory
import com.felipersn.idwallproject.presentation.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilder {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    //Add more ViewModels here.

}