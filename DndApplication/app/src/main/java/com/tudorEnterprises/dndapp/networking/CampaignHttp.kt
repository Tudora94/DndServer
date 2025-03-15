package com.tudorEnterprises.dndapp.networking

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.dataModels.requests.CreateCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCampaignResponse
import com.tudorEnterprises.dndapp.objects.RetroFitHttpCampaignClient
import com.tudorEnterprises.dndapp.objects.SecureStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CampaignHttp(val context: Context) {
    private val campaignService = RetroFitHttpCampaignClient.create(context)

    suspend fun newCampaign(campaignName: String,localCampaignId: Int,) : Int {
        val userId = SecureStorage.getUserId(context).toInt()
        val response = withContext(Dispatchers.IO) {
            campaignService.createCampaign(CreateCampaignRequest(localCampaignId, userId, campaignName))
        }

        return if(response.isSuccessful) {
            Log.i("CampaignHttp", "campaignId = ${response.body()?.campaignId}")
            response.body()?.campaignId ?: 0
        } else {
            Log.i("CampaignHttp", "Create Campaign Failed = ${response.code()}")
            0
        }
    }

    suspend fun getCampaigns() : List<CreateCampaignResponse>? {
        val userId = SecureStorage.getUserId(context).toInt()
        val response = withContext(Dispatchers.IO) {
            campaignService.getCampaignsForUser(userId)
        }

        return if(response.isSuccessful) {
            Log.d("CampaignHttp", "${response.body()}")
            response.body()
        } else {
            null
        }
    }
}