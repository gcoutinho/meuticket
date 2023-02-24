package com.meuticket.pos.register.products.presentation

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.meuticket.pos.R
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityRegisterProductsBinding
import com.meuticket.pos.register.products.presentation.adapter.ProductsRegisterAdapter
import com.meuticket.pos.ui.components.ViewDialog
import com.meuticket.pos.ui.utils.hideKeyboard

class ProductsRegisterActivity: BaseMvvmActivity() {

    val binding: ActivityRegisterProductsBinding by viewBinding(ActivityRegisterProductsBinding::inflate)

    val viewModel: ProductsRegisterViewModel by appViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        
        setupListener()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state) {
                is ProductsRegisterViewModelState.ConfirmDelete -> {
                    showDeleteDialog(state.action)
                }
                is ProductsRegisterViewModelState.OpenEditScreen -> {
                    formIntent.launch(ProductFormActivity.newIntent(this, state.product))
                }
                ProductsRegisterViewModelState.ProductsLoaded -> {
                    loadItems()
                }
                ProductsRegisterViewModelState.ProductInCartError -> {
                    showProductInCartError()
                }
            }
        })
    }

    private fun showDeleteDialog(action: () -> Unit) {
        val dialog = ViewDialog()
        dialog.showNow(supportFragmentManager, "DIALOG")

        dialog.apply {
            title = "Atenção"
            description = "Deseja excluir o registro?"
            primaryButtonText = "Sim"
            setPrimaryButtonListener {
                action.invoke()
                dismissAllowingStateLoss()
            }
            secondaryButtonText = "Não"
            setSecondaryButtonListener {
                dismissAllowingStateLoss()
            }
        }
    }

    val formIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == RESULT_OK)
            viewModel.loadItems()
    }

    private fun showProductInCartError() {
        ViewDialog().apply {
            showNow(supportFragmentManager, "DIALOG")
            title = "Atenção"
            description = "Este produto está no carrinho, remova-o antes de fazer essa operação"
            primaryButtonText = "OK"
            setPrimaryButtonListener {
                dismissAllowingStateLoss()
            }
        }
    }

    private fun setupListener() {
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
    }

    private fun loadItems() {
        binding.productsList.layoutManager = LinearLayoutManager(this)
        binding.productsList.adapter = ProductsRegisterAdapter(viewModel)
    }

    private fun doSearch(text: String) {
        (binding.productsList.adapter as ProductsRegisterAdapter).filter(text)
    }

}