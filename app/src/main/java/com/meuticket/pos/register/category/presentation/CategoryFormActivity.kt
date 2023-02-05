package com.meuticket.pos.register.category.presentation

import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ActivityCategoryFormBinding
import com.meuticket.pos.shared.data.model.Category

class CategoryFormActivity: BaseMvvmActivity() {

    val binding: ActivityCategoryFormBinding by viewBinding(ActivityCategoryFormBinding::inflate)

    val viewModel: CategoryFormViewModel by appViewModel()

    var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {

    }
}