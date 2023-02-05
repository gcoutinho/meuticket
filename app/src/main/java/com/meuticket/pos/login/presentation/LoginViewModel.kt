package com.meuticket.pos.login.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.login.domain.LoginInteractor
import androidx.lifecycle.viewModelScope
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginViewModelState {
    class LoginSuccess(val user: User): LoginViewModelState()
    class LoginError(val message: String): LoginViewModelState()
}

class LoginViewModel @Inject constructor(
    val interactor: LoginInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<LoginViewModelState>()

    fun doLogin(user: String, password: String) {

        viewModelScope.launch {
            runOn(Dispatchers.IO) {
                interactor.doLogin(user, password)
            }.onSuccess { result ->
                if(result.isValid)
                    state.value = LoginViewModelState.LoginSuccess(result.user!!)
                else
                    state.value = LoginViewModelState.LoginError(result.errorMessage!!)
            }.onFailure {
                state.value = LoginViewModelState.LoginError(it.message?:"")
            }

        }
    }
}