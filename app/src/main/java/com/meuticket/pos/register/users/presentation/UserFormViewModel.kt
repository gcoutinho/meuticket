package com.meuticket.pos.register.users.presentation

import android.content.res.Resources
import com.meuticket.pos.R
import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.model.User
import com.meuticket.pos.shared.domain.UsersListInteractor
import javax.inject.Inject

sealed class UserFormViewModelState {
    class InputError(val message: String): UserFormViewModelState()
    object SavedSuccess: UserFormViewModelState()
}

class UserFormViewModel @Inject constructor(
    val resources: Resources,
    val interactor: UsersListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<UserFormViewModelState>()
    fun insertOrUpdate(user: User?, username: String, password: String, isAdmin: Boolean) {

        if(username.length < 3 || password.length < 4) {
            state.value = UserFormViewModelState.InputError(message = resources.getString(R.string.user_validation_message))
        } else {
            runAsync(
                {
                    interactor.insertOrUpdate(user, username, password, isAdmin)
                }, onSuccess = {
                    state.value = UserFormViewModelState.SavedSuccess
                }
            )
        }
    }
}