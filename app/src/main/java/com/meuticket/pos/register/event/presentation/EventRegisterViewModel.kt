package com.meuticket.pos.register.event.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.model.Event
import com.meuticket.pos.shared.domain.EventListInteractor
import javax.inject.Inject

sealed class EventRegisterViewModelState {
    class OpenEditScreen(val event: Event): EventRegisterViewModelState()
    class ConfirmDelete(val event: Event): EventRegisterViewModelState()
}

class EventRegisterViewModel @Inject constructor(
    val interactor: EventListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<EventRegisterViewModelState>()

    lateinit var event: List<Event>

    override fun onCreate() {
        super.onCreate()
        runAsync({
            interactor.listEvents()
        }, onSuccess = {
            event = it
        })
    }

    fun editClicked(event: Event) {
        state.value = EventRegisterViewModelState.OpenEditScreen(event)
    }

    fun deleteClicked(event: Event) {
        state.value = EventRegisterViewModelState.ConfirmDelete(event)
    }

}
