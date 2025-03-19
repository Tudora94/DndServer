package com.tudorEnterprises.dndapp.networking

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.dataModels.requests.CreateCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.requests.DeleteCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCampaignResponse
import com.tudorEnterprises.dndapp.objects.RetroFitHttpCampaignClient
import com.tudorEnterprises.dndapp.objects.SecureStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CampaignHttp(val context: Context) {
    private val campaignService = RetroFitHttpCampaignClient.create(context)

    suspend fun newCampaign(campaignName: String, updateTime: Long) : Int {
        val userId = SecureStorage.getUserId(context).toInt()
        val response = withContext(Dispatchers.IO) {
            campaignService.createCampaign(CreateCampaignRequest(userId, campaignName, updateTime))
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

        return if(response.isSuccessful) { //TODO amend to contain body()?.Successful also, to stop accidental deletions
            Log.d("CampaignHttp", "${response.body()}")
            if(response.body() != null){
                    Log.d("CampaignHttp", "getCallMade")
            }
            response.body()
        } else {
            null
        }
    }

    suspend fun deleteCampaign(campaignId: Int) : Boolean {
        val request = DeleteCampaignRequest(SecureStorage.getUserId(context).toInt(), campaignId)

        val response = withContext(Dispatchers.IO) {
            campaignService.deleteCampaignById(request)
        }
        return response.body()?.success == true
    }
}