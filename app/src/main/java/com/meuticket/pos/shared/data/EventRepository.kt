package com.meuticket.pos.shared.data

import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.model.Event
import javax.inject.Inject

interface EventRepository {
    fun listFromRemote(): List<Event>
    fun listFromLocal(): List<Event>
}

class EventRepositoryImpl @Inject constructor(
    val localStorage: LocalStorage,
    ): EventRepository {

    override fun listFromRemote(): List<Event> {
        return mutableListOf(
            Event(
                1,
                "show 1"
            ),
            Event(
                2,
                "show 2"
            ),
        )
    }

    override fun listFromLocal(): List<Event> {
        return localStorage.getEvents()
    }
}