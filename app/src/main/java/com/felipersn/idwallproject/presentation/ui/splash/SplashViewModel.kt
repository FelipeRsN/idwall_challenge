package com.felipersn.idwallproject.presentation.ui.splash

import android.content.SharedPreferences
import com.felipersn.idwallproject.common.di.module.SharedPreferenceModule
import com.felipersn.idwallproject.presentation.base.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val splashSharedPreference: SharedPreferences) : BaseViewModel() {

    fun validateLoginState(): Boolean {
        return splashSharedPreference.getBoolean(SharedPreferenceModule.SHARED_PREFERENCE_LOGIN_STATE, false)
    }

}
