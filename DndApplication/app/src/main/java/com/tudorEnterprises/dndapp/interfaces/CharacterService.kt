package com.tudorEnterprises.dndapp.interfaces

import com.tudorEnterprises.dndapp.dataModels.requests.CreateCharacterRequest
import com.tudorEnterprises.dndapp.dataModels.requests.DeleteCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.responses.BaseResponse
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCampaignResponse
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCharacterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CharacterService {
    @POST("/api/Player/CreatePlayer")
    suspend fun createCharacter(@Body request: CreateCharacterRequest): Response<CreateCharacterResponse>

    @GET("api/CampaignNew/GetCampaigns/{userId}")
    suspend fun getCampaignsForUser(@Path("userId") userId: Int): Response<List<CreateCampaignResponse>?>

    @POST("api/CampaignNew/DeleteCampaign")
    suspend fun deleteCampaignById(@Body request: DeleteCampaignRequest): Response<BaseResponse>
}