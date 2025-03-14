package com.tudorEnterprises.dndapp.networking

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.dataModels.requests.CreateCampaignRequest
import com.tudorEnterprises.dndapp.objects.RetroFitHttpCampaignClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CampaignHttp() {

    suspend fun newCampaign(campaignName: String, context: Context) : Boolean {
        val campaignService = RetroFitHttpCampaignClient.create(context)
        val response = withContext(Dispatchers.IO) {
            campaignService.createCampaign(CreateCampaignRequest(0, "bb", campaignName)) //TODO pass in the userId given at login.
        }

        return if(response.isSuccessful) {
            Log.i("CampaignHttp", "campaignId = ${response.body()?.campaignId}")
            true
        } else {
            Log.i("CampaignHttp", "Create Campaign Failed = ${response.code()}")
            false
        }
    }
}