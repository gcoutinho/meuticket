package com.meuticket.pos.core.session

import com.meuticket.pos.shared.data.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Session @Inject constructor(

) {
    lateinit var loggedUser: User
}