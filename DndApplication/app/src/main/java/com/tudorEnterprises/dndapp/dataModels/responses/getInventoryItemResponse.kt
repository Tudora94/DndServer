package com.tudorEnterprises.dndapp.dataModels.responses

data class getInventoryItemResponse(val inventoryItems: List<InventoryItemResponse>, val message : String, val success: Boolean)
data class InventoryItemResponse(
    val id : Int,
    val campaignId: Int,
    val playerId: Int,
    val itemName: String,
    val itemDescription: String,
    val itemDetail: String,
    val updateTime: Long
)