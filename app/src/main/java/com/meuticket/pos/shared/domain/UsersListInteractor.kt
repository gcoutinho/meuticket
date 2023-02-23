package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.UsersRepository
import com.meuticket.pos.shared.data.model.User
import javax.inject.Inject

interface UsersListInteractor {
    fun listUsers(): List<User>
    fun saveOrUpdate(user: User?, username: String, password: String, admin: Boolean)
    fun delete(user: User)
}

class UsersListInteractorImpl @Inject constructor(
    val repository: UsersRepository
): UsersListInteractor {

    override fun listUsers(): List<User> {
        return repository.listUsersFromLocal()
    }

    override fun saveOrUpdate(user: User?, username: String, password: String, admin: Boolean) {
        repository.saveOrUpdate(user, username, password, admin)
    }

    override fun delete(user: User) {
        repository.deleteUser(user)
    }

}
