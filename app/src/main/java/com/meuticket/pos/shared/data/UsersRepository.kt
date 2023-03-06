package com.meuticket.pos.shared.data

import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.model.User
import javax.inject.Inject

interface UsersRepository {
    suspend fun listUsersFromRemote(): List<User>
    suspend fun listUsersFromLocal(): List<User>
    suspend fun insertOrUpdate(user: User?, username: String, password: String, admin: Boolean)
    suspend fun deleteUser(user: User)
}

class UsersRepositoryImpl @Inject constructor(
    val localStorage: LocalStorage,
    ): UsersRepository {
    override suspend fun listUsersFromRemote(): List<User> {
        return mutableListOf(
            User(
                uid = "aaaa-aaaa-aaaa", "admin", "1234", true
            ),
            User(
                uid = "1111-111-1111", "teste", "1234", false
            ),
        )
    }

    override suspend fun listUsersFromLocal(): List<User> {
        return localStorage.getUsers()
    }

    override suspend fun insertOrUpdate(user: User?, username: String, password: String, admin: Boolean) {
        if(user == null) {
            localStorage.saveUser(username, password, admin)
        } else {
            localStorage.updateUser(user.uid, username, password, admin)
        }
    }

    override suspend fun deleteUser(user: User) {
        localStorage.deleteUser(user)
    }
}