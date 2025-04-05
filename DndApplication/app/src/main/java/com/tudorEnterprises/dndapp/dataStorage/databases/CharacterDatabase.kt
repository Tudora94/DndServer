package com.tudorEnterprises.dndapp.dataStorage.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tudorEnterprises.dndapp.dataStorage.interfaces.CharacterDao
import com.tudorEnterprises.dndapp.dataStorage.tables.CharacterNameData

@Database(entities = [CharacterNameData:: class], version = 4)
abstract class CharacterDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}