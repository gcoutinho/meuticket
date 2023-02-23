package com.meuticket.pos.shared.data

import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.shared.data.model.User
import javax.inject.Inject

interface UsersRepository {
    fun listUsersFromRemote(): List<User>
    fun listUsersFromLocal(): List<User>
    fun saveOrUpdate(user: User?, username: String, password: String, admin: Boolean)
    fun deleteUser(user: User)
}

class UsersRepositoryImpl @Inject constructor(
    val localStorage: LocalStorage,
    ): UsersRepository {
    override fun listUsersFromRemote(): List<User> {
        return mutableListOf(
            User(
                uid = 1, "admin", "1234", true
            ),
            User(
                uid = 2, "teste", "1234", false
            ),
        )
    }

    override fun listUsersFromLocal(): List<User> {
        return localStorage.getUsers()
    }

    override fun saveOrUpdate(user: User?, username: String, password: String, admin: Boolean) {
        if(user == null) {
            localStorage.saveUser(username, password, admin)
        } else {
            localStorage.updateUser(user.uid, username, password, admin)
        }
    }

    override fun deleteUser(user: User) {
        localStorage.deleteUser(user)
    }
}