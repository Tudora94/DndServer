package com.tudorEnterprises.dndapp.networking

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.dataModels.requests.CreateItemRequest
import com.tudorEnterprises.dndapp.objects.InventoryItem
import com.tudorEnterprises.dndapp.objects.RetroFitHttpCharacterClient
import com.tudorEnterprises.dndapp.objects.SecureStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InventoryHttp(val context: Context) {
    private val InventoryService = RetroFitHttpCharacterClient.RetroFitHttpInventoryClient.create(context)

    suspend fun createNewItem(campaignId : Int, item: InventoryItem, updateTime: Long) : Int {
        val userId = SecureStorage.getUserId(context).toInt()
        val response = withContext(Dispatchers.IO) {
            InventoryService.createInventoryItem(CreateItemRequest(userId, updateTime, campaignId, item.name, item.description, item.detail))
        }

        return if (response.isSuccessful) {
            Log.i("InventoryHttp", "itemId = ${response.body()?.itemId}")
            response.body()?.itemId ?: 0
        } else {
            throw Exception("Failed to create item: ${response.errorBody()?.string()}")
        }

    }

}