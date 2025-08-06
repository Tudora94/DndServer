package com.tudorEnterprises.dndapp.dataStorage.interfaces

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignCharactersData
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignCharactersDao {
    @Upsert
    suspend fun insertCampaignPlayer(character: CampaignCharactersData)

    @Query("SELECT * FROM CampaignCharactersData WHERE userId = :userId AND campaignId = :campaignId")
    fun getPlayersForCampaign(userId: String, campaignId: Int) : Flow<List<CampaignCharactersData>>

    @Query("SELECT updateTime FROM CampaignCharactersData WHERE id = :id")
    fun getCharacterUpdateTime(id: Int) : Long

    @Query("DELETE FROM CampaignCharactersData WHERE playerId = :playerId AND campaignId = :campaignId")
    suspend fun deletePlayerFromCampaign(playerId: Int, campaignId: Int)
}