package com.tudorEnterprises.dndapp.dataModels.requests

data class AddPlayerToCampaignRequest(val userId: Int, val updateTime: Long, val characterId: Int, val roomCode: String)