package com.tudorEnterprises.dndapp.dataModels.responses

data class GetCharacterResponse(
    val success: Boolean,
    val players: List<Player>
)

data class Player(
    val id: Int,
    val campaignId: Int?, // Nullable to handle the `null` value
    val name: String,
    val updateTime: Long
)
