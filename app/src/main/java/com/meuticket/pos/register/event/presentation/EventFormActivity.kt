package com.meuticket.pos.register.event.presentation

import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.databinding.ActivityEventFormBinding
import com.meuticket.pos.shared.data.model.Event

class EventFormActivity: BaseMvvmActivity() {

    val binding: ActivityEventFormBinding by viewBinding(ActivityEventFormBinding::inflate)

    val viewModel: EventFormViewModel by appViewModel()

    var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {

    }
}