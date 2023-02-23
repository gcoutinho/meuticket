package com.meuticket.pos.register.users.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.core.session.Session
import com.meuticket.pos.shared.data.model.User
import com.meuticket.pos.shared.domain.UsersListInteractor
import javax.inject.Inject

sealed class UsersRegisterViewModelState {
    class OpenEditScreen(val user: User): UsersRegisterViewModelState()
    class ConfirmDelete(val user: User, val action: () -> Unit): UsersRegisterViewModelState()
    object UsersLoaded: UsersRegisterViewModelState()
    object DeleteMyselfError: UsersRegisterViewModelState()
}

class UsersRegisterViewModel @Inject constructor(
    val interactor: UsersListInteractor,
    val session: Session
): BaseViewModel() {

    val state = SingleLiveEvent<UsersRegisterViewModelState>()
    lateinit var users: List<User>

    override fun onCreate() {
        super.onCreate()

        loadItems()
    }

    fun editClicked(user: User) {
        state.value = UsersRegisterViewModelState.OpenEditScreen(user)
    }

    fun deleteClicked(user: User) {
        if(user.uid == session.loggedUser.uid) {
            state.value = UsersRegisterViewModelState.DeleteMyselfError
            return
        }
        state.value = UsersRegisterViewModelState.ConfirmDelete(user) {
            runAsync({
                interactor.delete(user)
            }, onSuccess = {
                loadItems()
            })

        }
    }

    fun loadItems() {
        runAsync(
            {
                interactor.listUsers()
            }, onSuccess = {
                users = it
                state.value = UsersRegisterViewModelState.UsersLoaded
            }
        )
    }

    fun canEdit(): Boolean = session.loggedUser.admin

}
