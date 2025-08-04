package com.tudorEnterprises.dndapp.networking

import android.content.Context
import com.tudorEnterprises.dndapp.objects.RetroFitHttpValidateClient

class ValidateHttp(val context: Context) {

    private val validateService = RetroFitHttpValidateClient.create(context)

    suspend fun validateToken(userId: String): Boolean {
        return try {
            val response = validateService.validateToken(userId)

            response.isSuccessful && response.body()?.success == true
        } catch (e: Exception) {
            false
        }
    }
}