package com.meuticket.pos.register.presentation

import android.content.Intent
import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ActivityRegisterBinding
import com.meuticket.pos.register.category.presentation.CategoryRegisterActivity
import com.meuticket.pos.register.event.presentation.EventRegisterActivity
import com.meuticket.pos.register.products.presentation.ProductsRegisterActivity
import com.meuticket.pos.register.users.presentation.UsersRegisterActivity

class RegisterActivity: BaseMvvmActivity() {

    val viewModel: BaseViewModel by appViewModel()
    val binding by viewBinding(ActivityRegisterBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        title = "Cadastros"

        binding.registerProducts.setOnClickListener {
            startActivity(Intent(this, ProductsRegisterActivity::class.java))
        }
        binding.registerUsers.setOnClickListener {
            startActivity(Intent(this, UsersRegisterActivity::class.java))
        }
        binding.registerCategories.setOnClickListener {
            startActivity(Intent(this, CategoryRegisterActivity::class.java))
        }
        binding.registerEvents.setOnClickListener {
            startActivity(Intent(this, EventRegisterActivity::class.java))
        }
    }
}