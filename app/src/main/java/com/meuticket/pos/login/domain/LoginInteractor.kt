package com.meuticket.pos.login.domain

import com.meuticket.pos.core.session.Session
import com.meuticket.pos.core.storage.LocalStorage
import com.meuticket.pos.login.data.model.LoginResult
import javax.inject.Inject

interface LoginInteractor {
    fun doLogin(user: String, password: String): LoginResult

}
class LoginInteractorImpl @Inject constructor(
    val localStorage: LocalStorage,
    val session: Session
): LoginInteractor {
    override fun doLogin(user: String, password: String): LoginResult {
        return localStorage.getUsers().find {
            it.name == user && it.password == password
        }?.let {
            session.loggedUser = it

            LoginResult(
                true,
                null,
                session.loggedUser
            )
        }?: LoginResult(
            false,
            "Usuário e/ou senha inválidos",
            null
        )
    }
}