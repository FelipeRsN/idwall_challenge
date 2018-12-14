package com.felipersn.idwallproject.presentation.ui.splash

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.felipersn.idwallproject.R
import com.felipersn.idwallproject.presentation.base.BaseActivity
import com.felipersn.idwallproject.presentation.ui.login.LoginActivity
import com.felipersn.idwallproject.presentation.ui.mainlist.MainListActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initView()
    }

    private fun initView() {
        setupViewModel()
        getLoginState()
    }

    private fun setupViewModel(){
        splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)
    }

    private fun getLoginState() {
        when (splashViewModel.validateLoginState()) {
            USER_LOGGED -> {
                startActivity(MainListActivity.provideIntent(baseContext))
            }
            USER_NOT_LOGGED -> {
                startActivity(LoginActivity.provideIntent(baseContext))
            }
        }

        finishAfterTransition()
    }

    companion object {
        private const val USER_LOGGED = true
        private const val USER_NOT_LOGGED = false
    }
}
