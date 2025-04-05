package com.tudorEnterprises.dndapp.interfaces

import com.tudorEnterprises.dndapp.dataModels.requests.CreateCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.requests.GetRoomCodeRequest
import com.tudorEnterprises.dndapp.dataModels.responses.BaseResponse
import com.tudorEnterprises.dndapp.dataModels.responses.CampaignCharacterResponse
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCampaignResponse
import com.tudorEnterprises.dndapp.dataModels.responses.GetRoomCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CampaignService {
    @POST("api/CampaignNew/CreateCampaign")
    suspend fun createCampaign(@Body request: CreateCampaignRequest): Response<CreateCampaignResponse>

    @GET("api/CampaignNew/GetCampaigns/{userId}")
    suspend fun getCampaignsForUser(@Path("userId") userId: Int): Response<List<CreateCampaignResponse>?>

    @DELETE("api/CampaignNew/DeleteCampaign/userId/{userId}/campaignId/{campaignId}")
    suspend fun deleteCampaignById(@Path("userId") userId: Int, @Path("campaignId") campaignId: Int): Response<BaseResponse>

    @POST("api/CampaignNew/GenerateCampaignCode")
    suspend fun generateRoomCode(@Body request: GetRoomCodeRequest) : Response<GetRoomCodeResponse>

    @GET("api/CampaignNew/GetPlayers/{campaignId}")
    suspend fun getPlayers(@Path("campaignId") campaignId: Int): Response<List<CampaignCharacterResponse>>
}