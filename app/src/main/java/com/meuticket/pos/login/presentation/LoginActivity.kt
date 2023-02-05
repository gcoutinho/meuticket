package com.meuticket.pos.login.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityLoginBinding
import com.meuticket.pos.home.presentation.HomeActivity
import com.meuticket.pos.ui.utils.hideKeyboard

class LoginActivity: BaseMvvmActivity() {

    val binding by viewBinding(ActivityLoginBinding::inflate)

    val viewModel: LoginViewModel by appViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListeners()

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state) {
                is LoginViewModelState.LoginError -> {
                    Log.e("ERRO", state.message)
                }
                is LoginViewModelState.LoginSuccess -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }
        })
    }

    private fun setupListeners() {
        binding.loginSubmit.setOnClickListener {
            hideKeyboard(it)
            viewModel.doLogin(
                binding.loginUser.text,
                binding.loginPassword.text
            )
        }
    }
}