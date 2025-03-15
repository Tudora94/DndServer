package com.tudorEnterprises.dndapp.dataStorage.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignNameData
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaign(campaign: CampaignNameData)

    @Query("SELECT * FROM CampaignNameData WHERE campaign_name = :campaignNameSelection LIMIT 1")
    suspend fun getCampaignByName(campaignNameSelection: String): CampaignNameData?

    @Query("DELETE FROM CampaignNameData")
    suspend fun clearCampaigns()

    @Query("SELECT * FROM CampaignNameData WHERE userId = :userId")
    fun getAllCampaigns(userId: String) : Flow<List<CampaignNameData>>

    @Upsert
    suspend fun upsertCampaign(campaign: CampaignNameData)
}