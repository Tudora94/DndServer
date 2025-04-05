package com.tudorEnterprises.dndapp.dataStorage.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CampaignNameData (
    @ColumnInfo(name = "campaign_name") val campaignName: String,
    @PrimaryKey @ColumnInfo(name = "sync_Campaign_Id") val syncCampaignId: Int = 0,
    @ColumnInfo(name = "userId") val userId: Int = 0,
    @ColumnInfo(name = "updateTime") val updateTime: Long = 0
)

@Entity
data class CharacterNameData (
    @ColumnInfo(name = "character_name") val characterName: String,
    @PrimaryKey @ColumnInfo(name = "sync_Character_Id") val syncCharacterId: Int,
    @ColumnInfo(name = "userId") val userId: Int = 0,
    @ColumnInfo(name = "updateTime") val updateTime: Long = 0,
    @ColumnInfo(name = "campaign_Id") val campaignId: Int? = null,
    @ColumnInfo(name = "campaign_name") val campaignName: String = ""
)