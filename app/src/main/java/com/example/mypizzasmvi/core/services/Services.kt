package com.example.mypizzasmvi.core.services

import com.example.mypizzasmvi.core.models.LoginModel
import com.example.mypizzasmvi.core.models.LoginResponse
import com.example.mypizzasmvi.core.models.PizzaModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Services {
    @POST("/v1/signin")
    suspend fun login(@Body request: LoginModel): Response<LoginResponse>
    @GET("/v1/pizza")
    suspend fun getListPizza(): Response<List<PizzaModel>>
}