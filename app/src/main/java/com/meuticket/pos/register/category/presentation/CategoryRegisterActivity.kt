package com.meuticket.pos.register.category.presentation

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
import com.meuticket.pos.databinding.ActivityRegisterCategoryBinding
import com.meuticket.pos.register.category.presentation.adapter.CategoryRegisterAdapter
import com.meuticket.pos.ui.components.ViewDialog
import com.meuticket.pos.ui.utils.hideKeyboard
import com.meuticket.pos.ui.utils.showAlertDialog

class CategoryRegisterActivity: BaseMvvmActivity() {

    val binding: ActivityRegisterCategoryBinding by viewBinding(ActivityRegisterCategoryBinding::inflate)

    val viewModel: CategoryRegisterViewModel by appViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListener()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state) {
                is CategoryRegisterViewModelState.ConfirmDelete -> {
                    showDeleteDialog(state.action)
                }
                is CategoryRegisterViewModelState.OpenEditScreen -> {
                    formIntent.launch(CategoryFormActivity.newIntent(this, state.category))
                }
                CategoryRegisterViewModelState.ShowErrorCategoryInCart -> {
                    showCategoryInCartError()
                }
                CategoryRegisterViewModelState.CategoriesLoaded -> {
                    loadItems()
                }
            }
        })
    }


    val formIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == RESULT_OK)
            viewModel.loadItems()
    }

    private fun showDeleteDialog(action: () -> Unit) {
        val dialog = ViewDialog()
        dialog.showNow(supportFragmentManager, "DIALOG")

        dialog.apply {
            title = getString(R.string.warning_alert_title)
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
    private fun showCategoryInCartError() {
        showAlertDialog("Esta categoria está no carrinho, remova-a antes de fazer essa operação")
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
        binding.categoryList.layoutManager = LinearLayoutManager(this)
        binding.categoryList.adapter = CategoryRegisterAdapter(viewModel)
    }

    private fun doSearch(text: String) {
        (binding.categoryList.adapter as CategoryRegisterAdapter).filter(text)
    }

}