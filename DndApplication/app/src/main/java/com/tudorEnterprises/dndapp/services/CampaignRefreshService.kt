package com.tudorEnterprises.dndapp.services

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.dataStorage.CampaignSqlActivity
import com.tudorEnterprises.dndapp.networking.CampaignHttp
import kotlinx.coroutines.flow.first

class CampaignRefreshService(val context: Context, val dbCalls: CampaignSqlActivity) {

    private val httpCalls = CampaignHttp(context)

    suspend fun fetchFromServerAndUpdatedDb() {
        Log.d("refresh Service", "refresh service called")
        val campaignList = httpCalls.getCampaigns()
        if (campaignList != null) {
            val localCampaigns = dbCalls.getAllCampaigns()
            val serverIds = campaignList.map { it.campaignId }.toSet()

            val campaignsToDelete =
                localCampaigns.first().filter { it.syncCampaignId !in serverIds }
            if (campaignsToDelete.isNotEmpty()) {
                for (campaign in campaignsToDelete) {
                    Log.d("campaign Deletion", "deleting campaign ${campaign.syncCampaignId}")
                    dbCalls.deleteCampaignById(campaign.syncCampaignId)
                }
            }

            for (campaign in campaignList) {

                //TODO api and local change needed to include last Updated Date as epoch time
                dbCalls.upsertCampaign(campaign)
            }
        }
    }
}