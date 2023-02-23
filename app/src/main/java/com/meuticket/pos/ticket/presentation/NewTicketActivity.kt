package com.meuticket.pos.ticket.presentation

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meuticket.pos.R
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.cart.presentation.CartActivity
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityNewTicketBinding
import com.meuticket.pos.payment.presentation.PaymentActivity
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.ticket.presentation.adapter.CategoriesAdapter
import com.meuticket.pos.ticket.presentation.adapter.ProductsAdapter
import com.meuticket.pos.ui.utils.animateClick
import com.meuticket.pos.ui.utils.hide
import com.meuticket.pos.ui.utils.hideKeyboard
import com.meuticket.pos.ui.utils.show
import com.meuticket.pos.utils.toFormattedCurrency
import java.math.BigDecimal
import java.util.*

class NewTicketActivity: BaseMvvmActivity() {

    val viewModel by appViewModel<ProductListViewModel>()

    val binding by viewBinding(ActivityNewTicketBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        title = "Nova Venda"

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
             when(state) {
                 is ProductListViewModelState.ProductsLoaded -> {
                     loadItems()
                 }
                 is ProductListViewModelState.ShowQuantityPicker -> {
                     showQuantityPicker(state.product)
                 }
                 is ProductListViewModelState.HideQuantityPicker -> {
                     hideQuantityPicker()
                 }
                 is ProductListViewModelState.TempCart -> {
                     updateTempCart(state.quantity, state.value)
                 }
                 is ProductListViewModelState.CartUpdated -> {
                     cartUpdated(state.value)
                 }
                 is ProductListViewModelState.CategoryClicked -> {
                     doFilter(state.category, state.isSelected)
                 }
             }
        })
    }

    private fun cartUpdated(value: Double) {
        binding.pay.text = BigDecimal(value).toFormattedCurrency(locale = Locale.getDefault())
    }

    private fun updateTempCart(quantity: Int, value: Double) {
        binding.qtd.text = quantity.toString()
        binding.addToCart.text = BigDecimal(value).toFormattedCurrency(locale = Locale.getDefault())
    }

    private fun hideQuantityPicker() {
        binding.newTicketQtdFooter.hide()
        binding.newTicketPayFooter.show()
    }

    private fun showQuantityPicker(product: Product) {
        binding.newTicketPayFooter.hide()
        binding.newTicketQtdFooter.show()

        binding.removeOne.setOnClickListener {
            it.animateClick(duration = 100).withEndAction { viewModel.removeOne(product) }
        }
        binding.addOne.setOnClickListener {
            it.animateClick(duration = 100).withEndAction { viewModel.addOne(product) }
        }

        binding.addToCart.setOnClickListener {
            viewModel.addToCart(product, binding.qtd.text.toString().toInt())
            (binding.productsList.adapter as ProductsAdapter).resetSelection()
        }
    }

    private fun loadItems() {
        binding.productsList.layoutManager = LinearLayoutManager(this)
        binding.productsList.adapter = ProductsAdapter(viewModel)

        binding.categoryList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.categoryList.adapter = CategoriesAdapter(viewModel)
    }

    fun setupListeners() {
        binding.search.findViewById<EditText>(R.id.input_edit_text).apply {
            setOnEditorActionListener { _, id, _ ->
                if(id == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch(binding.search.text)
                    hideKeyboard(this)
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            addTextChangedListener { doSearch(binding.search.text) }
        }
        binding.pay.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        binding.cart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun doSearch(text: String) {
        hideQuantityPicker()
        (binding.productsList.adapter as ProductsAdapter).filter(text)
    }

    private fun doFilter(category: Category, isSelected: Boolean) {
        hideQuantityPicker()
        (binding.productsList.adapter as ProductsAdapter).filter(category, isSelected)
    }

}