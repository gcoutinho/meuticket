package com.meuticket.pos.register.products.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.widget.ScrollView
import androidx.appcompat.widget.LinearLayoutCompat
import com.meuticket.pos.R
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityProductFormBinding
import com.meuticket.pos.databinding.ViewDialogSelectorItemBinding
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.shared.data.model.Product
import com.meuticket.pos.ui.utils.showAlertDialog
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

        if(product != null)
            binding.submit.text = getString(R.string.update_button)

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
                is ProductFormViewModelState.Error -> {
                    showAlertDialog(state.message)
                }
                ProductFormViewModelState.SavedSuccess -> {
                    showSuccessDialog()
                }
            }
        })
    }

    private fun showSuccessDialog() {
        showAlertDialog(
            title = getString(R.string.success),
            message = getString(R.string.saved_success_message),
            primaryButtonAction = {
                setResult(RESULT_OK)
                finish()
            }
        )
    }

    private fun setupView() {
        binding.name.text = product?.name?:""
        binding.value.text = BigDecimal(product?.value?:0.0).toFormattedCurrency(locale = Locale.getDefault())
        binding.category.text = product?.category?.name?:""
        binding.printReceipt.isChecked = product?.printReceipt?:false
    }

    private fun setupListeners() {
        binding.category.apply {

            binding.inputEditText.isFocusableInTouchMode = false
            binding.inputEditText.isFocusable = false

            val onClick = OnClickListener {
                viewModel.categoryClicked()
            }

            binding.inputEditText.setOnClickListener(onClick)
            setOnClickListener(onClick)
        }

        binding.value.addTextWatcher(MoneyWatcher(binding.value.editText))
        
        binding.submit.setOnClickListener { 
            viewModel.save(product, binding.name.text, binding.value.text, binding.category.text, binding.printReceipt.isChecked)
        }
    }

    private fun showCategoriesSelector(categories: List<Category>) {
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

    companion object {
        fun newIntent(context: Context, product: Product? = null) =
            Intent(context, ProductFormActivity::class.java).apply {
                putExtra(PRODUCT_EXTRA, product)
            }

        const val PRODUCT_EXTRA = "product_extra"
    }
}