package com.meuticket.pos.payment.presentation

import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ActivityPaymentBinding

class PaymentActivity: BaseMvvmActivity() {

    val viewModel: PaymentViewModel by appViewModel()

    val binding: ActivityPaymentBinding by viewBinding(ActivityPaymentBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.paymentCash.setOnClickListener { viewModel.payWithCash() }
        binding.paymentCredit.setOnClickListener { viewModel.payWithCredit() }
        binding.paymentDebit.setOnClickListener { viewModel.payWithDebit() }
        binding.paymentPix.setOnClickListener { viewModel.payWithPix() }
        binding.paymentCortesy.setOnClickListener { viewModel.payWithCortesy() }
        binding.paymentOthers.setOnClickListener { viewModel.payWithOthers() }
        binding.paymentFinish.setOnClickListener { viewModel.finishPayment() }
    }
}