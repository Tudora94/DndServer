package com.tudorEnterprises.dndapp.interfaces

import com.tudorEnterprises.dndapp.dataModels.Responses.LoginResponse
import com.tudorEnterprises.dndapp.dataModels.requests.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/auth/Login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}