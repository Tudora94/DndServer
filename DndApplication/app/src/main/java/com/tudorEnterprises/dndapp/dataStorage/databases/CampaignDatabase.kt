package com.tudorEnterprises.dndapp.dataStorage.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignNameData
import com.tudorEnterprises.dndapp.dataStorage.interfaces.CampaignDao

@Database(entities = [CampaignNameData:: class], version = 6)
abstract class CampaignDatabase : RoomDatabase() {
    abstract val campaignDao: CampaignDao
}