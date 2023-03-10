package com.meuticket.pos.register.event.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.meuticket.pos.R
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityEventFormBinding
import com.meuticket.pos.shared.data.model.Event
import com.meuticket.pos.ui.components.ViewDialog
import com.meuticket.pos.ui.utils.showAlertDialog

class EventFormActivity: BaseMvvmActivity() {

    val binding: ActivityEventFormBinding by viewBinding(ActivityEventFormBinding::inflate)

    val viewModel: EventFormViewModel by appViewModel()

    var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        event = intent.getSerializableExtra(EXTRA_EVENT) as Event?
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when (state) {
                EventFormViewModelState.SavedSuccess -> {
                    showSuccessDialog()
                }
                is EventFormViewModelState.ShowError -> {
                    showAlertDialog(state.message)
                }
            }
        })
    }
    private fun showSuccessDialog() {
        showAlertDialog(
            title = getString(R.string.success),
            message = getString(R.string.saved_success_message),
            primaryButtonAction = {
                setResult(RESULT_OK)
                finish()
            }
        )
    }

    private fun setupListeners() {

        binding.name.text = event?.name?:""
        binding.submit.setOnClickListener {
            viewModel.save(event, binding.name.text)
        }
    }

    companion object {

        const val EXTRA_EVENT = "extra_event"
        fun newIntent(context: Context, event: Event? = null): Intent {
            return Intent(context, EventFormActivity::class.java).apply {
                putExtra(EXTRA_EVENT, event)
            }
        }
    }
}