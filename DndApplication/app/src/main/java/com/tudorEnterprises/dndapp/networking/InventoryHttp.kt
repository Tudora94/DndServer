package com.tudorEnterprises.dndapp.networking

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.dataModels.requests.CreateItemRequest
import com.tudorEnterprises.dndapp.dataModels.responses.InventoryItemResponse
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

    suspend fun getItemsForCampaign(campaignId: Int): List<InventoryItemResponse>? {
        val userId = SecureStorage.getUserId(context).toInt()

        val response = withContext(Dispatchers.IO) {
            InventoryService.getItemsForCampaign(campaignId, userId)
        }

        return if (response.isSuccessful) {
            response.body()?.inventoryItems ?: emptyList()
        } else {
            Log.e("InventoryHttp", "Failed to fetch items for campaign: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun getItemsForPlayer(playerId: Int?, campaignId: Int): List<InventoryItemResponse>? {
        val userId = SecureStorage.getUserId(context).toInt()

        val response = withContext(Dispatchers.IO) {
            InventoryService.getItemsForPlayer(playerId, userId, campaignId)
        }

        return if (response.isSuccessful) {
            response.body()?.inventoryItems ?: emptyList()
        } else {
            Log.e("InventoryHttp", "Failed to fetch items for player: ${response.errorBody()?.string()}")
            emptyList()
        }
    }

    suspend fun deleteItem(itemId: Int) : Boolean {
        Log.d("InventoryHttp", "Deleting item with ID: $itemId")
        val response = withContext(Dispatchers.IO) {
            InventoryService.deleteItemById(SecureStorage.getUserId(context).toInt(),itemId)
        }
        return response.body()?.success == true
    }

}