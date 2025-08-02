package com.tudorEnterprises.dndapp.dataStorage.interfaces

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tudorEnterprises.dndapp.dataStorage.tables.InventoryItemData
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Upsert
    suspend fun insertInventoryItem(item: InventoryItemData)

    @Query("SELECT update_time FROM InventoryItemData WHERE itemId = :id")
    fun getItemUpdateTime(id: Int) : Long

    @Query("SELECT * FROM InventoryItemData WHERE campaign_id = :campaignId")
    fun getAllItemsByCampaign(campaignId: Int): Flow<List<InventoryItemData>>

    @Query("SELECT * FROM InventoryItemData WHERE character_id = :characterId AND campaign_id = :campaignId")
    fun getAllItemsByCharacter(characterId: Int?, campaignId: Int): Flow<List<InventoryItemData>>

    @Query("DELETE FROM InventoryItemData WHERE itemId = :itemId")
    suspend fun deleteItemById(itemId: Int)
}