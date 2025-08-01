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
    @ColumnInfo(name = "campaign_name") val campaignName: String? = ""
)

@Entity
data class CampaignCharactersData (
    @PrimaryKey val id : Int,
    val userId: String,
    val campaignId: Int?,
    val playerId: Int?,
    val characterName: String?,
    val updateTime: Long = 0
)

@Entity
data class InventoryItemData (
    @PrimaryKey val itemId: Int,
    @ColumnInfo(name = "campaign_id") val campaignId: Int,
    @ColumnInfo(name = "character_id") val characterId: Int?,
    @ColumnInfo(name = "item_name") val itemName: String,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "detail") val detail: String? = null,
    @ColumnInfo(name = "update_time") val updateTime: Long = 0
)