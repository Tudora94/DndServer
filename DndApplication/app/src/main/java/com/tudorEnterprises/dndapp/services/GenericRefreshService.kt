package com.tudorEnterprises.dndapp.services

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.constants.UserRole
import com.tudorEnterprises.dndapp.dataModels.responses.InventoryItemResponse
import com.tudorEnterprises.dndapp.dataStorage.CampaignCharacterSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.InventorySqlActivity
import com.tudorEnterprises.dndapp.networking.CampaignHttp
import com.tudorEnterprises.dndapp.networking.InventoryHttp
import kotlinx.coroutines.flow.first

class GenericRefreshService(val context: Context) {

    suspend fun fetchAndUpdateCampaignCharacters(dbCalls: CampaignCharacterSqlActivity, campaignId: Int) {
        val httpCalls = CampaignHttp(context)
        val characterList = httpCalls.getCampaignCharacters(campaignId = campaignId)

        for(character in characterList) {

                dbCalls.insertCharacterData(campaignId, character.characterName, character.id, character.updateTime)
//            }
        }
    }

    suspend fun fetchAndUpdateInventoryItems(dbCalls: InventorySqlActivity, id: Int, userRole: String, playerCampaignId : Int? = null) {
        val httpCalls = InventoryHttp(context)

        //check if the user is a DM, if so, fetch all items for campaign else fetch only the items for the character
        if(userRole == UserRole.DUNGEON_MASTER.role) {
            val itemList = httpCalls.getItemsForCampaign(id)

            if (itemList != null) { // retrieved list from server is not null, compare to local and delete if needed then add/ update remaining items

                checkAndDeleteItems(itemList, dbCalls, id, userRole)

                for(item in itemList) {
                    dbCalls.checkAndInsertInventoryItem(id, item.itemName, item.itemDescription, item.itemDetail, item.id, item.updateTime, item.playerId)
                }
            }
        }
        else if (userRole == UserRole.PLAYER.role) {
            val itemList = httpCalls.getItemsForPlayer(id, playerCampaignId ?: 0)

            if (itemList != null) { // retrieved list from server is not null, compare to local and delete if needed then add/ update remaining items

                checkAndDeleteItems(itemList, dbCalls, id, userRole, playerCampaignId)

                for(item in itemList) {
                    dbCalls.checkAndInsertInventoryItem(item.campaignId, item.itemName, item.itemDescription, item.itemDetail, item.id, item.updateTime, item.playerId)
                }
            }
        }
    }

    private suspend fun checkAndDeleteItems(itemList: List<InventoryItemResponse>, dbCalls: InventorySqlActivity, id: Int, userRole: String, playerCampaignId: Int? = null) {
        val localItems = dbCalls.getInventoryItemsById(id, userRole, playerCampaignId)
        val serverIds = itemList.map { it.id }.toSet()

        val itemsToDelete = localItems.first().filter { it.itemId !in serverIds }
        if(itemsToDelete.isNotEmpty()) {
            for(item in itemsToDelete) {
                Log.d("item Deletion", "deleting item ${item.itemId}")

                dbCalls.deleteItemById(item.itemId)
            }
        }

    }
}