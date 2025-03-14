package com.tudorEnterprises.dndapp.dataStorage.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignNameData

@Dao
interface CampaignDao {
    @Insert
    suspend fun insertCampaign(campaign: CampaignNameData)

    @Query("SELECT * FROM CampaignNameData WHERE campaign_name = :campaignNameSelection LIMIT 1")
    suspend fun getCampaignByName(campaignNameSelection: String): CampaignNameData?

    @Query("DELETE FROM CampaignNameData")
    suspend fun clearCampaigns()
}