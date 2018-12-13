package com.felipersn.idwallproject.presentation.ui.login

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.felipersn.idwallproject.R
import com.felipersn.idwallproject.common.extension.toast
import com.felipersn.idwallproject.common.tools.Resource
import com.felipersn.idwallproject.databinding.ActivityLoginBinding
import com.felipersn.idwallproject.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
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
        setupDataBinding()
        setupListeners()
        setupObservers()
    }

    private fun setupDataBinding() {
        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        activityLoginBinding.let {
            it.loginViewModelInView = loginViewModel
            it.setLifecycleOwner(this)
        }

        loginViewModel.init()
    }

    private fun setupListeners() {
        editTextMailAddress.setOnEditorActionListener { _, _, _ ->
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
                            //TODO save token and start new activity
                        }
                        Resource.Status.LOADING -> {
                            activityLoginBinding.progressBarLoader.visibility = View.VISIBLE
                            enableFields(false)
                        }
                        Resource.Status.ERROR -> {
                            activityLoginBinding.progressBarLoader.visibility = View.GONE
                            enableFields(true)
                            result.error?.let { message ->
                                toast(message)
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
}
