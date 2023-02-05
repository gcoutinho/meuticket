package com.meuticket.pos.register.users.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.model.User
import com.meuticket.pos.shared.domain.UsersListInteractor
import javax.inject.Inject

sealed class UsersRegisterViewModelState {
    class OpenEditScreen(val user: User): UsersRegisterViewModelState()
    class ConfirmDelete(val user: User): UsersRegisterViewModelState()
}

class UsersRegisterViewModel @Inject constructor(
    val interactor: UsersListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<UsersRegisterViewModelState>()

    fun editClicked(user: User) {
        state.value = UsersRegisterViewModelState.OpenEditScreen(user)
    }

    fun deleteClicked(user: User) {
        state.value = UsersRegisterViewModelState.ConfirmDelete(user)
    }

    var users: List<User> = interactor.listUsers()
}
