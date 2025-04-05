package com.tudorEnterprises.dndapp.dataModels.responses

data class AddPlayerToCampaignResponse(
    val success: Boolean = false, val campaignId: Int = 0, val campaignName: String = ""
)