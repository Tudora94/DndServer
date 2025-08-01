package com.tudorEnterprises.dndapp.dataStorage.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tudorEnterprises.dndapp.dataStorage.interfaces.InventoryDao
import com.tudorEnterprises.dndapp.dataStorage.tables.InventoryItemData


@Database(entities = [InventoryItemData:: class], version = 1)
abstract class InventoryDatabase : RoomDatabase() {
    abstract val inventoryDao: InventoryDao
}