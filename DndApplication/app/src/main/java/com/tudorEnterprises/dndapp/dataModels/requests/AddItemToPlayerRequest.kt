package com.tudorEnterprises.dndapp.dataModels.requests

data class AddItemToPlayerRequest(
    val userId: Int,
    val updateTime: Long,
    val campaignId: Int,
    val playerId: Int?,
    val itemId: Int
)