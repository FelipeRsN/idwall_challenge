package com.felipersn.idwallproject.data.store.remote.repository.login

import com.felipersn.idwallproject.data.store.remote.dto.SignUpResponseDTO
import com.felipersn.idwallproject.data.store.remote.services.LoginService
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginService: LoginService) {

    fun signUp(mail: String): Maybe<SignUpResponseDTO> {
        val remote = signUpRemote(mail)

        return Observable.concatArray(remote)
            .filter { list -> list != null }
            .firstElement()
    }

    private fun signUpRemote(mail: String): Observable<SignUpResponseDTO> {
        return loginService.signUp(mail)
            .cache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}