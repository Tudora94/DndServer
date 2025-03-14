package com.tudorEnterprises.dndapp.dataStorage.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CampaignNameData (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "campaign_name") val campaignName: String,
    @ColumnInfo(name = "sync_Campaign_Id") val syncCampaignId: Int = 0,
)