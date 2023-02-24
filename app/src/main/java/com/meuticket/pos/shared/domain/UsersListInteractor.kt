package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.UsersRepository
import com.meuticket.pos.shared.data.model.User
import javax.inject.Inject

interface UsersListInteractor {
    suspend fun listUsers(): List<User>
    suspend fun insertOrUpdate(user: User?, username: String, password: String, admin: Boolean)
    suspend fun delete(user: User)
}

class UsersListInteractorImpl @Inject constructor(
    val repository: UsersRepository
): UsersListInteractor {

    override suspend fun listUsers(): List<User> {
        return repository.listUsersFromLocal()
    }

    override suspend fun insertOrUpdate(user: User?, username: String, password: String, admin: Boolean) {
        repository.insertOrUpdate(user, username, password, admin)
    }

    override suspend fun delete(user: User) {
        repository.deleteUser(user)
    }

}
