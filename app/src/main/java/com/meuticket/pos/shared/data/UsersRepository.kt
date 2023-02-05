package com.meuticket.pos.shared.data

import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.model.User
import javax.inject.Inject

interface UsersRepository {
    fun listUsersFromRemote(): List<User>
    fun listUsersFromLocal(): List<User>
}

class UsersRepositoryImpl @Inject constructor(
    val localStorage: LocalStorage,
    ): UsersRepository {
    override fun listUsersFromRemote(): List<User> {
        return mutableListOf(
            User(
                "admin", "1234", true
            ),
            User(
                "teste", "1234", false
            ),
        )
    }

    override fun listUsersFromLocal(): List<User> {
        return localStorage.getUsers()
    }
}