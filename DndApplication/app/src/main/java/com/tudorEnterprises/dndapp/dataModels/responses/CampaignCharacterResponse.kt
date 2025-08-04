package com.tudorEnterprises.dndapp.dataModels.responses

@Suppress("unused")
data class CampaignCharacterResponse(
    val id: Int,
    val username: String,
    val firstName: String,
    val characterName: String,
    val updateTime: Long,
)