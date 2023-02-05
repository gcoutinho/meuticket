package com.meuticket.pos.shared.domain

import com.meuticket.pos.shared.data.UsersRepository
import com.meuticket.pos.shared.data.model.User
import javax.inject.Inject

interface UsersListInteractor {
    fun listUsers(): List<User>
}

class UsersListInteractorImpl @Inject constructor(
    val repository: UsersRepository
): UsersListInteractor {

    override fun listUsers(): List<User> {
        return repository.listUsersFromLocal()
    }

}
