package com.tudorEnterprises.dndapp.objects

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit

object SecureStorage {
    private fun getPreferences(context: Context) =
        EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveToken(context: Context, token: String) {
        getPreferences(context).edit { putString("jwt_token", token) }
    }

    fun getToken(context: Context): String? {
        return getPreferences(context).getString("jwt_token", null)
    }

    fun clearToken(context: Context) {
        getPreferences(context).edit { remove("jwt_token") }
    }

    fun saveRefreshToken(context: Context, token: String) {
        getPreferences(context).edit { putString("refresh_token", token) }
    }

    fun getRefreshToken(context: Context): String? {
        return getPreferences(context).getString("refresh_token", null)
    }

    fun clearRefreshToken(context: Context) {
        getPreferences(context).edit { remove("refresh_token") }
    }

    fun saveUserId(context: Context, userId: String) {
        getPreferences(context).edit { putString("userId", userId) }
    }

    fun getUserId(context: Context): String {
        return getPreferences(context).getString("userId", null) ?: "0"
    }

    fun clearUserId(context: Context) {
        getPreferences(context).edit { remove("userId") }
    }

}