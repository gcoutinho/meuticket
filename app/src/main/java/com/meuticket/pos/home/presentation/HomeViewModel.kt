package com.meuticket.pos.home.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.core.session.Session
import javax.inject.Inject

sealed class HomeViewModelState {
    class RegisterVisibility(val isVisible: Boolean): HomeViewModelState()
    class SettingsVisibility(val isVisible: Boolean): HomeViewModelState()
    class AdminVisibility(val isVisible: Boolean): HomeViewModelState()
}

class HomeViewModel @Inject constructor(
    val session: Session
): BaseViewModel() {

    val state = SingleLiveEvent<HomeViewModelState>()

    override fun onResume() {
        super.onResume()

        state.value = HomeViewModelState.RegisterVisibility(session.loggedUser.admin)
        state.value = HomeViewModelState.SettingsVisibility(session.loggedUser.admin)
        state.value = HomeViewModelState.AdminVisibility(session.loggedUser.admin)
    }
}