package com.felipersn.idwallproject.login

import android.content.SharedPreferences
import com.felipersn.idwallproject.common.extension.validateMailAddress
import com.felipersn.idwallproject.data.store.remote.dto.login.SignUpResponseDTO
import com.felipersn.idwallproject.data.store.remote.dto.login.SignUpUserDTO
import com.felipersn.idwallproject.data.store.remote.repository.login.LoginRepository
import com.felipersn.idwallproject.data.store.remote.services.LoginService
import com.felipersn.idwallproject.presentation.ui.login.LoginViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class LoginViewModelTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    @Mock
    private lateinit var loginService: LoginService

    @Mock
    private lateinit var loginRepository: LoginRepository

    @Mock
    private lateinit var loginSharedPreferences: SharedPreferences

    @InjectMocks
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var mailAddressTypedMock: String
    private lateinit var signUpResponseDTOMock: SignUpResponseDTO
    private lateinit var throwableMock: Throwable


    @Before
    fun setUp() {
        mailAddressTypedMock = "mymailaddressmock@mockmail.com"

        signUpResponseDTOMock = SignUpResponseDTO(
            SignUpUserDTO(
                "2018-12-17T12:20:50.599Z",
                0,
                "5c1794a25a94011273a5cfda",
                "mymailaddressmock@mockmail.com",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpZGRvZy1zZXJ2aWNlIiwic3ViIjoiNWMxNzk0YTI1YTk0MDExMjczYTVjZmRhIiwiaWF0IjoxNTQ1MDQ5MjUwLCJleHAiOjE1NDYzNDUyNTB9.kDWfLF3Bglb_-AHcgekduwcNppjP3qakD39DolcyPCY",
                "2018-12-17T12:20:50.599Z"
            )
        )

        throwableMock = mock()
    }


}