package com.meuticket.pos.cart.presentation

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.meuticket.pos.R
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.cart.presentation.adapter.CartAdapter
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityCartBinding
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.ui.components.ViewDialog
import com.meuticket.pos.ui.utils.animateClick
import com.meuticket.pos.ui.utils.hide
import com.meuticket.pos.ui.utils.show
import com.meuticket.pos.utils.toFormattedCurrency
import java.math.BigDecimal
import java.util.*

class CartActivity: BaseMvvmActivity() {

    val viewModel: CartViewModel by appViewModel()

    val binding: ActivityCartBinding by viewBinding(ActivityCartBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupObservers()
        setupListeners()
    }

    private fun loadItems() {
        binding.cartList.layoutManager = LinearLayoutManager(this)
        binding.cartList.adapter = CartAdapter(viewModel)
        binding.pay.text = BigDecimal(viewModel.totalCart()).toFormattedCurrency(locale = Locale.getDefault())
    }

    private fun setupListeners() {

    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state) {
                is CartViewModelState.CartLoaded -> {
                    loadItems()
                }
                is CartViewModelState.ShowQuantityPicker -> {
                    showQuantityPicker(state.product)
                }
                is CartViewModelState.HideQuantityPicker -> {
                    hideQuantityPicker()
                }
                is CartViewModelState.TempCart -> {
                    updateTempCart(state.quantity, state.value)
                }
                is CartViewModelState.ShowDeleteDialog -> {
                    val dialog = ViewDialog()
                    dialog.showNow(supportFragmentManager, "DIALOG")

                    dialog.apply {
                        title = getString(R.string.warning_alert_title)
                        description = "Deseja remover o item do carrinho?"
                        primaryButtonText = "Sim"
                        setPrimaryButtonListener {
                            state.action.invoke()
                            dismissAllowingStateLoss()
                            hideQuantityPicker()
                        }
                        secondaryButtonText = "Não"
                        setSecondaryButtonListener {
                            dismissAllowingStateLoss()
                        }
                    }
                }
            }
        })
    }

    private fun updateTempCart(quantity: Int, value: Double) {
        binding.qtd.text = quantity.toString()
        binding.addToCart.text = "Atualizar ${BigDecimal(value).toFormattedCurrency(locale = Locale.getDefault())}"
    }

    private fun hideQuantityPicker() {
        binding.cartQtdFooter.hide()
        binding.cartPayFooter.show()
    }

    private fun showQuantityPicker(product: Product) {
        binding.cartPayFooter.hide()
        binding.cartQtdFooter.show()
        updateTempCart(product.qtd, product.qtd*product.value)

        binding.removeOne.setOnClickListener {
            it.animateClick(duration = 100).withEndAction { viewModel.removeOne(binding.qtd.text.toString(), product) }
        }
        binding.addOne.setOnClickListener {
            it.animateClick(duration = 100).withEndAction { viewModel.addOne(binding.qtd.text.toString(), product) }
        }

        binding.addToCart.setOnClickListener {
            viewModel.updateCart(product, binding.qtd.text.toString().toInt())
            hideQuantityPicker()
        }
    }

}