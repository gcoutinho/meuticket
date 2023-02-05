package com.meuticket.pos.login.data.model

import com.meuticket.pos.shared.data.model.User

data class LoginResult(
    val isValid: Boolean,
    val errorMessage: String?,
    val user: User?
)