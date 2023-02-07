package com.meuticket.pos.register.products.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityProductFormBinding
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.utils.MoneyWatcher
import com.meuticket.pos.utils.toFormattedCurrency
import java.math.BigDecimal
import java.util.*

class ProductFormActivity: BaseMvvmActivity() {

    val binding: ActivityProductFormBinding by viewBinding(ActivityProductFormBinding::inflate)

    val viewModel: ProductFormViewModel by appViewModel()

    var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        product = intent.getSerializableExtra(PRODUCT_EXTRA) as Product?

        setupListeners()
        setupView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state) {
                is ProductFormViewModelState.ShowCategoriesSelector -> {
                    showCategoriesSelector(state.categories)
                }
            }
        })
    }

    private fun setupView() {
        binding.name.text = product?.name?:""
        binding.value.text = BigDecimal(product?.value?:0.0).toFormattedCurrency(locale = Locale.getDefault())
        binding.category.text = product?.category?.name?:""
        binding.print2ndWay.isChecked = product?.secondWay?:false
    }

    private fun setupListeners() {
        binding.category.apply {

            binding.inputEditText.isFocusableInTouchMode = false
            binding.inputEditText.isFocusable = false

            setOnClickListener {
                viewModel.categoryClicked()
            }
        }

        binding.value.addTextWatcher(MoneyWatcher(binding.value.editText))
    }

    private fun showCategoriesSelector(categories: List<Category>) {

    }

    companion object {
        fun newIntent(context: Context, product: Product? = null) =
            Intent(context, ProductFormActivity::class.java).apply {
                putExtra(PRODUCT_EXTRA, product)
            }

        const val PRODUCT_EXTRA = "product_extra"
    }
}