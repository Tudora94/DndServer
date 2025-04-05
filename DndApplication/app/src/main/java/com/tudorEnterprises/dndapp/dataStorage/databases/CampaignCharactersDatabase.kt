package com.tudorEnterprises.dndapp.dataStorage.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tudorEnterprises.dndapp.dataStorage.interfaces.CampaignCharactersDao
import com.tudorEnterprises.dndapp.dataStorage.tables.CampaignCharactersData

@Database(entities = [CampaignCharactersData:: class], version = 2)
abstract class CampaignCharactersDatabase : RoomDatabase() {
    abstract val campaignCharactersDao: CampaignCharactersDao
}