package com.tudorEnterprises.dndapp.dataStorage

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.tudorEnterprises.dndapp.constants.UserRole
import com.tudorEnterprises.dndapp.dataStorage.databases.InventoryDatabase
import com.tudorEnterprises.dndapp.dataStorage.tables.InventoryItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class InventorySqlActivity(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        InventoryDatabase::class.java,
        "inventory_database"
    )
        .fallbackToDestructiveMigration(true)
        .build()

    suspend fun checkAndInsertInventoryItem(campaignInt: Int, itemName: String, description: String, detail: String, itemId: Int, updateTime: Long, characterId: Int? = null) {

        withContext(Dispatchers.IO) {

            try {
                if(validateInventoryItemExists(itemId, updateTime)) {
                    Log.d(this::class.java.simpleName, "Inventory item with id $itemId already exists with the same update time.")
                    return@withContext
                }
                db.inventoryDao.insertInventoryItem(
                    InventoryItemData(
                        itemId = itemId,
                        campaignId = campaignInt,
                        characterId = characterId, //defaults to null when created by DM, but will have a value when created by player screen
                        itemName = itemName,
                        description = description,
                        detail = detail,
                        updateTime = updateTime
                    )
                )
                Log.d(this::class.java.simpleName, "Inventory item saved successfully with id $itemId")
            } catch (ex: Exception) {
                Log.d(this::class.java.simpleName, "Inventory save failed with exception: $ex")
            }
        }
    }

    fun getInventoryItemsById(id: Int, userRole: String, playerCampaignId: Int? = null) : Flow<List<InventoryItemData>> {
        //TODO this is used by the DM and Player screen, currently only passes one ID either campaign or player
        Log.d(this::class.java.simpleName, "user role is $userRole matching with ${UserRole.DUNGEON_MASTER.role}")

        return if(userRole == UserRole.DUNGEON_MASTER.role) {
            db.inventoryDao.getAllItemsByCampaign(id)
        } else {
            db.inventoryDao.getAllItemsByCharacter(id, playerCampaignId)
        }
    }

    private fun validateInventoryItemExists(
        itemId: Int,
        updateTime: Long
    ): Boolean {
        return db.inventoryDao.getItemUpdateTime(
            id = itemId,
        ) == updateTime
    }

    suspend fun deleteItemById(itemId: Int) {
            db.inventoryDao.deleteItemById(itemId)
    }

    suspend fun assignItemToPlayer(itemId: Int, characterId: Int?, campaignId: Int, updateTime: Long)
    {
        db.inventoryDao.assignItemToPlayer(
            itemId = itemId,
            playerId = characterId,
            campaignId = campaignId,
            updateTime = updateTime
        )
    }

}

