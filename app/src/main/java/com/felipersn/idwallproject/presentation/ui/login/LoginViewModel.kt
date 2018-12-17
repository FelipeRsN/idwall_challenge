package com.felipersn.idwallproject.presentation.ui.login

import android.content.SharedPreferences
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.felipersn.idwallproject.common.di.module.SharedPreferenceModule
import com.felipersn.idwallproject.common.extension.validateMailAddress
import com.felipersn.idwallproject.common.tools.Resource
import com.felipersn.idwallproject.common.tools.SingleLiveEvent
import com.felipersn.idwallproject.data.store.remote.dto.login.SignUpResponseDTO
import com.felipersn.idwallproject.data.store.remote.repository.login.LoginRepository
import com.felipersn.idwallproject.presentation.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val loginSharedPreferences: SharedPreferences
) : BaseViewModel() {

    //Bind variables
    var typedMailAddress: String = ""
    val validMailAddress = ObservableBoolean()

    //LiveData variables
    val signUpLiveData = MutableLiveData<SingleLiveEvent<Resource<String>>>()

    fun init() {
        validMailAddress.set(true)
    }

    //Button click listener
    fun executeLogin() {
        if (typedMailAddress.validateMailAddress()) {
            validMailAddress.set(true)
            signUp()
        } else {
            validMailAddress.set(false)
        }
    }

    private fun signUp() {
        signUpLiveData.value = SingleLiveEvent(Resource.loading())

        addDisposable(loginRepository
            .signUp(typedMailAddress)
            .subscribe(
                { result -> onSuccessSignUp(result) },
                { throwable -> onErrorSignUp(throwable) }
            ))
    }

    private fun onSuccessSignUp(signUpResponseDTO: SignUpResponseDTO) {
        if (signUpResponseDTO.user?.token != null) {
            saveLoginState(signUpResponseDTO.user.token)
            signUpLiveData.value = SingleLiveEvent(Resource.success(signUpResponseDTO.user.token))
        } else {
            signUpLiveData.value = SingleLiveEvent(Resource.error())
        }

    }

    private fun onErrorSignUp(throwable: Throwable){
        signUpLiveData.value = SingleLiveEvent(Resource.error(throwable.message))
    }

    private fun saveLoginState(token: String) {
        loginSharedPreferences.edit().putBoolean(SharedPreferenceModule.SHARED_PREFERENCE_LOGIN_STATE, true).apply()
        loginSharedPreferences.edit().putString(SharedPreferenceModule.SHARED_PREFERENCE_LOGIN_TOKEN, token).apply()
    }

}
