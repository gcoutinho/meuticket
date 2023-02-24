package com.meuticket.pos.shared.data

import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.model.Event
import javax.inject.Inject

interface EventRepository {
    suspend fun listFromRemote(): List<Event>
    suspend fun listFromLocal(): List<Event>
}

class EventRepositoryImpl @Inject constructor(
    val localStorage: LocalStorage,
    ): EventRepository {

    override suspend fun listFromRemote(): List<Event> {
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

    override suspend fun listFromLocal(): List<Event> {
        return localStorage.getEvents()
    }
}