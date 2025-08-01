package com.tudorEnterprises.dndapp.interfaces

import com.tudorEnterprises.dndapp.dataModels.requests.CreateItemRequest
import com.tudorEnterprises.dndapp.dataModels.responses.CreateItemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface InventoryService {

    //TODO add the methods for the InventoryService interface based off CharacterService interface
    @POST("/api/Inventory/CreateInventoryItem")
    suspend fun createInventoryItem(@Body request: CreateItemRequest): Response<CreateItemResponse>
}