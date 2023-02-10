package com.meuticket.pos.register.event.presentation

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
import com.meuticket.pos.databinding.ActivityRegisterEventBinding
import com.meuticket.pos.register.event.presentation.adapter.EventRegisterAdapter
import com.meuticket.pos.ui.utils.hideKeyboard

class EventRegisterActivity: BaseMvvmActivity() {

    val binding: ActivityRegisterEventBinding by viewBinding(ActivityRegisterEventBinding::inflate)

    val viewModel: EventRegisterViewModel by appViewModel()

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
                is EventRegisterViewModelState.ConfirmDelete -> {

                }
                is EventRegisterViewModelState.OpenEditScreen -> {
                    startActivity(Intent(this, EventFormActivity::class.java))
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
        binding.eventList.layoutManager = LinearLayoutManager(this)
        binding.eventList.adapter = EventRegisterAdapter(viewModel)
    }

    private fun doSearch(text: String) {
        (binding.eventList.adapter as EventRegisterAdapter).filter(text)
    }

}