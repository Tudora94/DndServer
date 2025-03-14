package com.tudorEnterprises.dndapp.dataStorage

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.tudorEnterprises.dndapp.dataStorage.databases.CampaignDatabase
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignNameData

class CampaignSqlActivity(context: Context) {

    val db = Room.databaseBuilder(
        context,
        CampaignDatabase::class.java,
        "campaign_database"
    ).build()

    suspend fun InsertAndRetrieveCampaignData(campaignName: String, syncCampaignId: Int) {
        db.campaignDao.insertCampaign(CampaignNameData(campaignName = campaignName, syncCampaignId = syncCampaignId))
        val campaignData = db.campaignDao.getCampaignByName(campaignName)
        if(campaignData != null){
            Log.d("campaignSave", "Campaign Saved successfully - syncId: ${campaignData.syncCampaignId}, name: ${campaignData.campaignName}, Id: ${campaignData.id}")
        }
    }
}