package com.meuticket.pos.payment.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ScrollView
import androidx.appcompat.widget.LinearLayoutCompat
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityPaymentBinding
import com.meuticket.pos.databinding.ViewDialogSelectorItemBinding
import com.meuticket.pos.shared.data.model.Order

class PaymentActivity: BaseMvvmActivity() {

    val viewModel: PaymentViewModel by appViewModel()

    val binding: ActivityPaymentBinding by viewBinding(ActivityPaymentBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state -> when (state) {
            is PaymentViewModelState.OrderUpdate -> {

            }
            is PaymentViewModelState.ShowInputValue -> {
                showInputDialog(state.mode, state.action)
            }
        }})
    }

    private fun showInputDialog(mode: Order.OrderPayment.Mode, action: (value: Double) -> Unit) {
        AlertDialog.Builder(this).apply {
            var dialog: AlertDialog? = null

            setView(ScrollView(this@ProductFormActivity).apply {
                addView(LinearLayoutCompat(this@ProductFormActivity).apply {
                    orientation = LinearLayoutCompat.VERTICAL
                    categories.forEach {category ->
                        addView(ViewDialogSelectorItemBinding.inflate(LayoutInflater.from(context)).apply {
                            title.text = category.name

                            setOnClickListener {
                                viewModel.categorySelected(category)
                                binding.category.text = category.name
                                dialog?.dismiss()
                            }
                        }.root)
                    }
                })
            })
            dialog = create()
            dialog.show()
        }
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