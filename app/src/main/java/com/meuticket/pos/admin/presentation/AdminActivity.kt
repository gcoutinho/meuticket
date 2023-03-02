package com.meuticket.pos.admin.presentation

import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityAdminBinding

class AdminActivity: BaseMvvmActivity() {

    val viewModel: AdminViewModel by appViewModel()

    val binding: ActivityAdminBinding by viewBinding(ActivityAdminBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when (state) {
                is AdminViewModelState.GoToCloseCash -> {

                }
                AdminViewModelState.GoToOpenCash -> {

                }
                is AdminViewModelState.ShowError -> {

                }
            }
        })
    }

    private fun setupListeners() {
        binding.adminOpenCash.setOnClickListener {
            viewModel.openCashClicked()
        }
        binding.adminCloseCash.setOnClickListener {
            viewModel.closeCashClicked()
        }
        binding.adminWithdraw.setOnClickListener {  }
        binding.adminInput.setOnClickListener {  }
        binding.adminCashBalance.setOnClickListener {  }
        binding.adminRollback.setOnClickListener {  }
        binding.adminReprint.setOnClickListener {  }
        binding.adminProductList.setOnClickListener {  }
    }
}