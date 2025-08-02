package com.tudorEnterprises.dndapp.interfaces

import com.tudorEnterprises.dndapp.dataModels.requests.CreateItemRequest
import com.tudorEnterprises.dndapp.dataModels.responses.BaseResponse
import com.tudorEnterprises.dndapp.dataModels.responses.CreateItemResponse
import com.tudorEnterprises.dndapp.dataModels.responses.getInventoryItemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface InventoryService {

    //TODO add the methods for the InventoryService interface based off CharacterService interface
    @POST("/api/Inventory/CreateInventoryItem")
    suspend fun createInventoryItem(@Body request: CreateItemRequest): Response<CreateItemResponse>

    @GET("/api/Inventory/GetCampaignInventory/{userId}/{campaignId}")
    suspend fun getItemsForCampaign(
        @Path("campaignId") campaignId: Int, @Path("userId") userId: Int
    ): Response<getInventoryItemResponse>

    @DELETE("/api/Inventory/DeleteInventoryItem/{userId}/{itemId}")
    suspend fun deleteItemById(
        @Path("userId") userId: Int, @Path("itemId") itemId: Int
    ): Response<BaseResponse>
}