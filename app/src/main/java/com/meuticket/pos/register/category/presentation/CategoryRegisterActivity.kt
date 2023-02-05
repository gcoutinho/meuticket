package com.meuticket.pos.register.category.presentation

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.meuticket.pos.R
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityRegisterCategoryBinding
import com.meuticket.pos.register.category.presentation.adapter.CategoryRegisterAdapter
import com.meuticket.pos.ui.utils.hideKeyboard

class CategoryRegisterActivity: BaseMvvmActivity() {

    val binding: ActivityRegisterCategoryBinding by viewBinding(ActivityRegisterCategoryBinding::inflate)

    val viewModel: CategoryRegisterViewModel by appViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        loadItems()

        setupListener()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state) {
                is CategoryRegisterViewModelState.ConfirmDelete -> {

                }
                is CategoryRegisterViewModelState.OpenEditScreen -> {
                    startActivity(Intent(this, CategoryFormActivity::class.java))
                }
            }
        })
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