package com.felipersn.idwallproject.presentation.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.felipersn.idwallproject.R
import com.felipersn.idwallproject.common.extension.isNetworkAvailable
import com.felipersn.idwallproject.common.extension.longToast
import com.felipersn.idwallproject.common.extension.toast
import com.felipersn.idwallproject.common.tools.Resource
import com.felipersn.idwallproject.databinding.ActivityLoginBinding
import com.felipersn.idwallproject.presentation.base.BaseActivity
import com.felipersn.idwallproject.presentation.ui.mainlist.MainListActivity
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        initView()
    }

    private fun initView() {
        setupViewModel()
        setupListeners()
        setupObservers()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        activityLoginBinding.let {
            it.loginViewModelInView = loginViewModel
            it.setLifecycleOwner(this)
        }

        loginViewModel.init()
    }

    private fun setupListeners() {
        activityLoginBinding.editTextMailAddress.setOnEditorActionListener { _, _, _ ->
            loginViewModel.executeLogin()
            true
        }
    }

    private fun setupObservers() {
        loginViewModel.signUpLiveData.observe(this, Observer { it ->
            it?.getContentIfNotHandled().let { resource ->
                resource?.let { result ->
                    when (result.status) {
                        Resource.Status.SUCCESS -> {
                            activityLoginBinding.progressBarLoader.visibility = View.GONE
                            enableFields(true)

                            proceedToMainList()
                        }
                        Resource.Status.LOADING -> {
                            activityLoginBinding.progressBarLoader.visibility = View.VISIBLE
                            enableFields(false)
                        }
                        Resource.Status.ERROR -> {
                            activityLoginBinding.progressBarLoader.visibility = View.GONE
                            enableFields(true)
                            if (isNetworkAvailable()) {
                                result.error?.let { message ->
                                    toast(message)
                                }
                            } else {
                                longToast(getString(R.string.offline))
                            }
                        }
                    }
                }
            }
        })
    }

    //enable or disable fields to waiting for api response
    private fun enableFields(enable: Boolean) {
        when (enable) {
            true -> {
                activityLoginBinding.editTextMailAddress.isEnabled = true
                activityLoginBinding.buttonEnter.isEnabled = true
            }
            false -> {
                activityLoginBinding.editTextMailAddress.isEnabled = false
                activityLoginBinding.buttonEnter.isEnabled = false
            }
        }
    }

    private fun proceedToMainList() {
        startActivity(MainListActivity.provideIntent(baseContext))
        finishAfterTransition()
    }

    companion object {

        fun provideIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}
