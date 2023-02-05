package com.meuticket.pos.register.products.presentation

import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ActivityProductFormBinding
import com.meuticket.pos.shared.data.model.Product

class ProductFormActivity: BaseMvvmActivity() {

    val binding: ActivityProductFormBinding by viewBinding(ActivityProductFormBinding::inflate)

    val viewModel: ProductFormViewModel by appViewModel()

    var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.category.apply {

            binding.inputEditText.isFocusableInTouchMode = false
            binding.inputEditText.isFocusable = false

            setOnClickListener {

            }
        }
    }
}