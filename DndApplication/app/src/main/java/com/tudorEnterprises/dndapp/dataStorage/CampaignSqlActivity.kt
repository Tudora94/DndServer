package com.tudorEnterprises.dndapp.dataStorage

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCampaignResponse
import com.tudorEnterprises.dndapp.dataStorage.databases.CampaignDatabase
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignNameData
import com.tudorEnterprises.dndapp.objects.SecureStorage
import kotlinx.coroutines.flow.Flow

class CampaignSqlActivity(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        CampaignDatabase::class.java,
        "campaign_database"
    )
        .fallbackToDestructiveMigration(true)
        .build()

    private val loggedInUser = SecureStorage.getUserId(context)

    suspend fun insertAndRetrieveCampaignData(campaignName: String, syncCampaignId: Int) {
        db.campaignDao.insertCampaign(CampaignNameData(campaignName = campaignName, syncCampaignId = syncCampaignId, userId = loggedInUser.toInt()))
        val campaignData = db.campaignDao.getCampaignByName(campaignName)
        if(campaignData != null){
            Log.d("campaignSave", "Campaign Saved successfully - syncId: ${campaignData.syncCampaignId}, name: ${campaignData.campaignName},")
        }
    }

    fun getAllCampaigns() : Flow<List<CampaignNameData>> {
        return db.campaignDao.getAllCampaigns(loggedInUser)
    }

    suspend fun upsertCampaign(campaignData: CreateCampaignResponse) {
        db.campaignDao.upsertCampaign(CampaignNameData(campaignName = campaignData.name, syncCampaignId = campaignData.campaignId, userId = loggedInUser.toInt()))
    }
}