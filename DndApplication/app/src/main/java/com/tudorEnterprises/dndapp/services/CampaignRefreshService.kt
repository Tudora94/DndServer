package com.tudorEnterprises.dndapp.services

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.dataStorage.CampaignSqlActivity
import com.tudorEnterprises.dndapp.networking.CampaignHttp

class CampaignRefreshService(val context: Context, val dbCalls: CampaignSqlActivity) {

    private val httpCalls = CampaignHttp(context)

    suspend fun fetchFromServerAndUpdatedDb() {
        Log.d("refresh Service", "refresh service called")
        val campaignList = httpCalls.getCampaigns()
        if (campaignList != null) {
            for(campaign in campaignList) {
                dbCalls.upsertCampaign(campaign)
            }
        }
    }
}