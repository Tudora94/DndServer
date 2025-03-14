package com.tudorEnterprises.dndapp.networking

import android.content.Context
import com.tudorEnterprises.dndapp.objects.SecureStorage
import okhttp3.Interceptor
import okhttp3.Response

class JwtHandler(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SecureStorage.getToken(context) // Get token using context
        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(request.build())
    }
}