package com.tudorEnterprises.dndapp.interfaces

import com.tudorEnterprises.dndapp.dataModels.requests.AddPlayerToCampaignRequest
import com.tudorEnterprises.dndapp.dataModels.requests.CreateCharacterRequest
import com.tudorEnterprises.dndapp.dataModels.responses.AddPlayerToCampaignResponse
import com.tudorEnterprises.dndapp.dataModels.responses.BaseResponse
import com.tudorEnterprises.dndapp.dataModels.responses.CreateCharacterResponse
import com.tudorEnterprises.dndapp.dataModels.responses.GetCharacterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CharacterService {
    @POST("/api/Player/CreatePlayer")
    suspend fun createCharacter(@Body request: CreateCharacterRequest): Response<CreateCharacterResponse>

    @GET("api/Player/GetPlayers/{userId}")
    suspend fun getCharactersForUser(@Path("userId") userId: Int): Response<GetCharacterResponse>

    @DELETE("api/Player/DeleteCharacter/userId/{userId}/characterId/{characterId}/updateTime/{updateTime}")
    suspend fun deleteCharacterById(@Path("userId") userId: Int, @Path("characterId") characterId: Int, @Path("updateTime") updateTime: Long): Response<BaseResponse>

    @PATCH("api/Player/AddPlayerToCampaign")
    suspend fun addCharacterToCampaign(@Body request: AddPlayerToCampaignRequest): Response<AddPlayerToCampaignResponse>
}