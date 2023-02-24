package com.meuticket.pos.register.users.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityUserFormBinding
import com.meuticket.pos.shared.data.model.User
import com.meuticket.pos.ui.components.ViewDialog
import kotlinx.android.synthetic.main.activity_user_form.*

class UserFormActivity: BaseMvvmActivity() {

    val binding: ActivityUserFormBinding by viewBinding(ActivityUserFormBinding::inflate)

    val viewModel: UserFormViewModel by appViewModel()

    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        user = intent.getSerializableExtra(USER_EXTRA) as User?

        if(user != null)
            binding.submit.text = "Atualizar"

        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver {state ->
            when(state) {
                is UserFormViewModelState.InputError -> {
                    showInputError(state.message)
                }
                UserFormViewModelState.SavedSuccess -> {
                    showSuccessDialog()
                }
            }
        })
    }

    private fun showSuccessDialog() {
        val dialog = ViewDialog()
        dialog.showNow(supportFragmentManager, "DIALOG")

        dialog.apply {
            title = "Sucesso"
            description = "Dados salvos com sucesso!"
            primaryButtonText = "OK"
            setPrimaryButtonListener {
                dismissAllowingStateLoss()
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun showInputError(message: String) {
        val dialog = ViewDialog()
        dialog.showNow(supportFragmentManager, "DIALOG")

        dialog.apply {
            title = "Atenção"
            description = message
            primaryButtonText = "Entendi"
            setPrimaryButtonListener {
                dismissAllowingStateLoss()
            }
        }
    }

    private fun setupListeners() {

        if(user != null) {
            binding.name.text = user!!.name
            binding.password.text = user!!.password
            binding.isAdmin.isChecked = user!!.admin
        }

        binding.submit.setOnClickListener {
            viewModel.insertOrUpdate(user, binding.name.text, binding.password.text, binding.isAdmin.isChecked)
        }
    }

    companion object {

        fun newIntent(context: Context, user: User?): Intent {
            return Intent(context, UserFormActivity::class.java).apply {
                putExtra(USER_EXTRA, user)
            }
        }

        const val USER_EXTRA = "user_extra"
    }
}