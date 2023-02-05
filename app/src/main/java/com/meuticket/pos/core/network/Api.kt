package com.meuticket.pos.core.network

import com.meuticket.pos.shared.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("/path")
    suspend fun listProducts(): Response<List<Product>>
}