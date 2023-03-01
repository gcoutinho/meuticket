package com.meuticket.pos.register.event.presentation

import com.meuticket.pos.base.BaseViewModel
import com.meuticket.pos.core.livedata.SingleLiveEvent
import com.meuticket.pos.shared.data.model.Event
import com.meuticket.pos.shared.domain.EventListInteractor
import javax.inject.Inject

sealed class EventFormViewModelState {
    class ShowError(val message: String): EventFormViewModelState()
    object SavedSuccess: EventFormViewModelState()
}
class EventFormViewModel @Inject constructor(
    val eventListInteractor: EventListInteractor
): BaseViewModel() {

    val state = SingleLiveEvent<EventFormViewModelState>()
    fun save(event: Event?, name: String) {
        runAsync({
            val eventExists = eventListInteractor.listEvents().firstOrNull { it.name == name }
            if(eventExists != null && eventExists.uid != (event?.uid?: 0))
                throw RuntimeException("Evento já existe")
            eventListInteractor.save(event, name)
        }, onSuccess = {
            state.value = EventFormViewModelState.SavedSuccess
        }, onError = {
            state.value = EventFormViewModelState.ShowError(it.message?:it.toString())
        })

    }
}