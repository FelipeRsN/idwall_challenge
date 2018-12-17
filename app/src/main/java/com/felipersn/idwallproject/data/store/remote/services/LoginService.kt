package com.felipersn.idwallproject.data.store.remote.services

import com.felipersn.idwallproject.data.store.remote.dto.login.SignUpResponseDTO
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @FormUrlEncoded
    @POST("signup")
    fun signUp(@Field("email") email: String?): Observable<SignUpResponseDTO>

}