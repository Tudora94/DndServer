package com.tudorEnterprises.dndapp.interfaces

import com.tudorEnterprises.dndapp.dataModels.requests.CreateCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCampaignResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CampaignService {
    @POST("api/CampaignNew/CreateCampaign")
    suspend fun createCampaign(@Body request: CreateCampaignRequest): Response<CreateCampaignResponse>
}