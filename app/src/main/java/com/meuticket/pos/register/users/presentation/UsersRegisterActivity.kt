package com.meuticket.pos.register.users.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.meuticket.pos.R
import com.meuticket.pos.base.BaseMvvmActivity
import com.meuticket.pos.base.viewBinding
import com.meuticket.pos.core.livedata.SafeObserver
import com.meuticket.pos.databinding.ActivityRegisterUsersBinding
import com.meuticket.pos.register.users.presentation.adapter.UsersRegisterAdapter
import com.meuticket.pos.ui.components.ViewDialog
import com.meuticket.pos.ui.utils.hideKeyboard

class UsersRegisterActivity: BaseMvvmActivity() {

    val binding: ActivityRegisterUsersBinding by viewBinding(ActivityRegisterUsersBinding::inflate)

    val viewModel: UsersRegisterViewModel by appViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupListener()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, SafeObserver { state ->
            when(state) {
                is UsersRegisterViewModelState.ConfirmDelete -> {
                    showDeleteDialog(state.action)
                }
                is UsersRegisterViewModelState.OpenEditScreen -> {

                    formIntent.launch(UserFormActivity.newIntent(this, state.user))
                }
                UsersRegisterViewModelState.UsersLoaded -> loadItems()
                UsersRegisterViewModelState.DeleteMyselfError -> deleteMyselfError()
            }
        })
    }

    private fun deleteMyselfError() {
        val dialog = ViewDialog()
        dialog.showNow(supportFragmentManager, "DIALOG")

        dialog.apply {
            title = "Atenção"
            description = "Não é possível deletar um usuário logado, entre com outra conta admin"
            primaryButtonText = "OK"
            setPrimaryButtonListener {
                dismissAllowingStateLoss()
            }
        }
    }

    val formIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == RESULT_OK)
            viewModel.loadItems()
    }

    private fun showDeleteDialog(action: () -> Unit) {
        val dialog = ViewDialog()
        dialog.showNow(supportFragmentManager, "DIALOG")

        dialog.apply {
            title = "Atenção"
            description = "Deseja excluir o registro?"
            primaryButtonText = "Sim"
            setPrimaryButtonListener {
                action.invoke()
                dismissAllowingStateLoss()
            }
            secondaryButtonText = "Não"
            setSecondaryButtonListener {
                dismissAllowingStateLoss()
            }
        }
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
        binding.usersList.layoutManager = LinearLayoutManager(this)
        binding.usersList.adapter = UsersRegisterAdapter(viewModel)
    }

    private fun doSearch(text: String) {
        try {
            (binding.usersList.adapter as UsersRegisterAdapter).filter(text)
        } catch (_: Exception) {}
    }

}