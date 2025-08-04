package com.tudorEnterprises.dndapp.objects

import android.content.Context
import com.tudorEnterprises.dndapp.constants.baseUrl
import com.tudorEnterprises.dndapp.interfaces.CampaignService
import com.tudorEnterprises.dndapp.interfaces.CharacterService
import com.tudorEnterprises.dndapp.interfaces.InventoryService
import com.tudorEnterprises.dndapp.interfaces.LoginService
import com.tudorEnterprises.dndapp.networking.JwtHandler
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitHttpAuthClient {
    val api: LoginService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)
    }
}

object RetroFitHttpValidateClient {
    fun create(context: Context): LoginService {
        val client = OkHttpClient.Builder()
            .addInterceptor(JwtHandler(context)) // Pass context
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl) // Replace with actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Attach OkHttpClient with the interceptor
            .build()
            .create(LoginService::class.java)
    }
}

object RetroFitHttpCampaignClient {
    fun create(context: Context): CampaignService {
        val client = OkHttpClient.Builder()
            .addInterceptor(JwtHandler(context)) // Pass context
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl) // Replace with actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Attach OkHttpClient with the interceptor
            .build()
            .create(CampaignService::class.java)
    }
}

object RetroFitHttpCharacterClient {
    fun create(context: Context): CharacterService {
        val client = OkHttpClient.Builder()
            .addInterceptor(JwtHandler(context)) // Pass context
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl) // Replace with actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Attach OkHttpClient with the interceptor
            .build()
            .create(CharacterService::class.java)
    }

    object RetroFitHttpInventoryClient {
        fun create(context: Context): InventoryService {
            val client = OkHttpClient.Builder()
                .addInterceptor(JwtHandler(context)) // Pass context
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl) // Replace with actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Attach OkHttpClient with the interceptor
                .build()
                .create(InventoryService::class.java)
        }
    }

}