package com.tudorEnterprises.dndapp.networking

import android.content.Context
import android.util.Log
import com.tudorEnterprises.dndapp.dataModels.requests.CreateCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.requests.GetRoomCodeRequest
import com.tudorEnterprises.dndapp.dataModels.responses.CampaignCharacterResponse
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCampaignResponse
import com.tudorEnterprises.dndapp.objects.RetroFitHttpCampaignClient
import com.tudorEnterprises.dndapp.objects.SecureStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

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

        var response : Response<List<CreateCampaignResponse>?>?
        try {

            response = withContext(Dispatchers.IO) {
                campaignService.getCampaignsForUser(userId)
            }
        } catch (ex: Exception) {
            Log.d("exception", ex.toString())
            response = null
        }

        return if(response != null) {
            if (response.isSuccessful) {
                Log.d("CampaignHttp", "${response.body()}")
                if (response.body() != null) {
                    Log.d("CampaignHttp", "getCallMade")
                }
                response.body()
            } else {
                null
            }
        } else {
            null
        }
    }

    suspend fun deleteCampaign(campaignId: Int) : Boolean {
        Log.d(this::class.java.simpleName,"campaignId sent for deletion $campaignId")
        val response = withContext(Dispatchers.IO) {
            campaignService.deleteCampaignById(SecureStorage.getUserId(context).toInt(), campaignId)
        }
        return response.body()?.success == true
    }

    suspend fun getRoomCode(campaignId: Int) : String? {
        val response = withContext(Dispatchers.IO) {
            campaignService.generateRoomCode(GetRoomCodeRequest(campaignId = campaignId))
        }
        if(response.isSuccessful) {
            return response.body()?.campaignRoomCode
        }
        return ""
    }

    suspend fun getCampaignCharacters(campaignId: Int) : List<CampaignCharacterResponse>?{
        val response = withContext(Dispatchers.IO) {
            campaignService.getPlayers(campaignId)
        }
        if (response.body() != null) {
            return response.body()
        }
        return null
    }
}