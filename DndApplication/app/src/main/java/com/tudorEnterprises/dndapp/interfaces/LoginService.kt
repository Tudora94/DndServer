package com.tudorEnterprises.dndapp.interfaces

import com.tudorEnterprises.dndapp.dataModels.responses.CreateUserResponse
import com.tudorEnterprises.dndapp.dataModels.responses.LoginResponse
import com.tudorEnterprises.dndapp.dataModels.requests.CreateUserRequest
import com.tudorEnterprises.dndapp.dataModels.requests.LoginRequest
import com.tudorEnterprises.dndapp.dataModels.responses.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @POST("api/auth/Login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/Register")
    suspend fun register(@Body request: CreateUserRequest): Response<CreateUserResponse>

    @GET("api/auth/validateToken/{userId}")
    suspend fun validateToken(@Path("userId") userId: String): Response<BaseResponse>
}