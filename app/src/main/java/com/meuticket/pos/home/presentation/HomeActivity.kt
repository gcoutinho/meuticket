package com.meuticket.pos.home.presentation

import android.content.Intent
import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityHomeBinding
import com.meuticket.pos.register.presentation.RegisterActivity
import com.meuticket.pos.ticket.presentation.NewTicketActivity
import com.meuticket.pos.ui.utils.hide

class HomeActivity: BaseMvvmActivity() {

    val viewModel: HomeViewModel by appViewModel()
    val binding by viewBinding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        title = "Meu Ticket"

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state) {
                is HomeViewModelState.AdminVisibility -> {
                    if(!state.isVisible)
                        binding.cardAdmin.alpha = 0.5f
                    else
                        binding.cardAdmin.setOnClickListener {  }
                }
                is HomeViewModelState.RegisterVisibility -> {
                    if(!state.isVisible)
                        binding.cardRegisters.alpha = 0.5f
                    else
                        binding.cardRegisters.setOnClickListener {
                            startActivity(Intent(this, RegisterActivity::class.java))
                        }

                }
                is HomeViewModelState.SettingsVisibility -> {
                    if(!state.isVisible)
                        binding.cardSettings.alpha = 0.5f
                    else
                        binding.cardSettings.setOnClickListener {  }
                }
            }
        })
    }

    private fun setupListeners() {
        binding.cardOrder.setOnClickListener {
            startActivity(Intent(this, NewTicketActivity::class.java))
        }
    }
}