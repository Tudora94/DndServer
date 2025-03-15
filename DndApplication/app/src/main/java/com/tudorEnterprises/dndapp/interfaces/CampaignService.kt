package com.tudorEnterprises.dndapp.interfaces

import com.tudorEnterprises.dndapp.dataModels.requests.CreateCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCampaignResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CampaignService {
    @POST("api/CampaignNew/CreateCampaign")
    suspend fun createCampaign(@Body request: CreateCampaignRequest): Response<CreateCampaignResponse>

    @GET("api/CampaignNew/GetCampaigns/{userId}")
    suspend fun getCampaignsForUser(@Path("userId") userId: Int): Response<List<CreateCampaignResponse>?>
}