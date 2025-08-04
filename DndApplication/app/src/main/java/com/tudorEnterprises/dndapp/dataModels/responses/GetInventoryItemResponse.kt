package com.tudorEnterprises.dndapp.dataModels.responses

@Suppress("unused")
data class GetInventoryItemResponse(val inventoryItems: List<InventoryItemResponse>, val message : String, val success: Boolean)
@Suppress("unused")
data class InventoryItemResponse(
    val id : Int,
    val campaignId: Int,
    val playerId: Int?,
    val itemName: String,
    val itemDescription: String,
    val itemDetail: String,
    val updateTime: Long
)