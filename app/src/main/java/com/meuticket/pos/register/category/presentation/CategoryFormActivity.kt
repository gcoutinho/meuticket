package com.meuticket.pos.register.category.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityCategoryFormBinding
import com.meuticket.pos.shared.data.model.Category
import com.meuticket.pos.ui.utils.showAlertDialog

class CategoryFormActivity: BaseMvvmActivity() {

    val binding: ActivityCategoryFormBinding by viewBinding(ActivityCategoryFormBinding::inflate)

    val viewModel: CategoryFormViewModel by appViewModel()

    var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        category = intent.getSerializableExtra(EXTRA_CATEGORY) as Category?

        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state) {
                is CategoryFormViewModelState.Error -> {
                    showAlertDialog(state.message)
                }
                CategoryFormViewModelState.SavedSuccess -> {
                    showSuccessDialog()
                }
            }
        })
    }

    private fun setupListeners() {
        binding.name.text = category?.name?:""

        binding.submit.setOnClickListener {
            viewModel.save(category, binding.name.text)
        }
    }

    private fun showSuccessDialog() {

        showAlertDialog(
            title = "Sucesso",
            message = "Dados salvos com sucesso!",
            primaryButtonAction = {
                setResult(RESULT_OK)
                finish()
            }
        )
    }

    companion object {
        fun newIntent(context: Context, category: Category? = null): Intent {
            return Intent(context, CategoryFormActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY, category)
            }
        }

        const val EXTRA_CATEGORY = "extra_category"
    }
}