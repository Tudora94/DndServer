package com.tudorEnterprises.dndapp.dataStorage

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.tudorEnterprises.dndapp.dataStorage.databases.CampaignCharactersDatabase
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignCharactersData
import com.tudorEnterprises.dndapp.objects.SecureStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CampaignCharacterSqlActivity(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        CampaignCharactersDatabase::class.java,
        "campaign_Characters_database"
    )
        .fallbackToDestructiveMigration(true)
        .build()

    private val loggedInUser = SecureStorage.getUserId(context)

    suspend fun insertCharacterData(campaignId: Int, characterName: String, characterId: Int, updateTime: Long) {
        withContext(Dispatchers.IO) {
            try {
                val primaryKey = "$campaignId$characterId".toInt()
                if (validateCharacterExists(campaignId, characterId, updateTime)) {
                    Log.d(this::class.java.simpleName, "character already exists with same update time")
                    return@withContext
                }
                db.campaignCharactersDao.insertCampaignPlayer(
                    CampaignCharactersData(
                        id = primaryKey,
                        userId = loggedInUser,
                        campaignId = campaignId,
                        playerId = characterId,
                        characterName = characterName,
                        updateTime = updateTime
                    )
                )
                Log.d(this::class.java.simpleName, "character saved successfully with id $characterId")
            } catch (ex: Exception) {
                Log.d(this::class.java.simpleName, "character save failed with exception: $ex")
            }
        }
    }

    fun getPlayersForCampaign(campaignId: Int) : Flow<List<CampaignCharactersData>> {
        return db.campaignCharactersDao.getPlayersForCampaign(loggedInUser, campaignId)
    }

    private fun validateCharacterExists(campaignId: Int, characterId: Int, updateTime: Long) : Boolean {
        val primaryKey = "$campaignId$characterId".toInt()
        return db.campaignCharactersDao.getCharacterUpdateTime(primaryKey)==updateTime

    }
}