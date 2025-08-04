package com.tudorEnterprises.dndapp.dataModels.requests

data class CreateItemRequest (val userId : Int,
                              val updateTime: Long,
                              val campaignId: Int,
                              val itemName: String,
                              val itemDescription: String,
                              val itemDetail: String)