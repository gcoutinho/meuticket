package com.meuticket.pos.register.users.presentation

import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ActivityUserFormBinding
import com.meuticket.pos.shared.data.model.User

class UserFormActivity: BaseMvvmActivity() {

    val binding: ActivityUserFormBinding by viewBinding(ActivityUserFormBinding::inflate)

    val viewModel: UserFormViewModel by appViewModel()

    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {

    }
}