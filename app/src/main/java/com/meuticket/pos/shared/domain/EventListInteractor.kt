package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.EventRepository
import com.meuticket.pos.shared.data.model.Event
import javax.inject.Inject

interface EventListInteractor {
    suspend fun listEvents(): List<Event>
}

class EventListInteractorImpl @Inject constructor(
    val repository: EventRepository
): EventListInteractor {

    override suspend fun listEvents(): List<Event> {
        return repository.listFromLocal()
    }

}
