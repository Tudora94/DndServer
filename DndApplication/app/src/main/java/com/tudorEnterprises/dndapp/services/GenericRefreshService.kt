package com.tudorEnterprises.dndapp.services

import android.content.Context
import com.tudorEnterprises.dndapp.constants.UserRole
import com.tudorEnterprises.dndapp.dataStorage.CampaignCharacterSqlActivity
import com.tudorEnterprises.dndapp.dataStorage.InventorySqlActivity
import com.tudorEnterprises.dndapp.networking.CampaignHttp
import com.tudorEnterprises.dndapp.networking.InventoryHttp

class GenericRefreshService(val context: Context) {

    suspend fun fetchAndUpdateCampaignCharacters(dbCalls: CampaignCharacterSqlActivity, campaignId: Int) {
        val httpCalls = CampaignHttp(context)
        val characterList = httpCalls.getCampaignCharacters(campaignId = campaignId)

        for(character in characterList) {

                dbCalls.insertCharacterData(campaignId, character.charachterName, character.id, character.updateTime)
//            }
        }
    }

    suspend fun fetchAndUpdateInventoryItems(dbCalls: InventorySqlActivity, id: Int, userRole: String) {
        val httpCalls = InventoryHttp(context)

        //check if the user is a DM, if so, fetch all items for campaign else fetch only the items for the character
        if(userRole == UserRole.DUNGEON_MASTER.role) {
            val itemList = httpCalls.getItemsForCampaign(id)

            if (itemList != null) {
                for(item in itemList) {
                    dbCalls.checkAndInsertInventoryItem(id, item.itemName, item.itemDescription, item.itemDetail, item.id, item.updateTime, item.playerId)
                }
            }
        }

//        val InventoryList = httpCalls.getInventoryItemsById(id, userRole)
    }
}