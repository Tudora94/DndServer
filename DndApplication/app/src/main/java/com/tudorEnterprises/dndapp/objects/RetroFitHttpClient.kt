package com.tudorEnterprises.dndapp.objects

import com.tudorEnterprises.dndapp.constants.baseUrl
import com.tudorEnterprises.dndapp.interfaces.LoginService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitHttpClient {
    val api: LoginService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)
    }
}