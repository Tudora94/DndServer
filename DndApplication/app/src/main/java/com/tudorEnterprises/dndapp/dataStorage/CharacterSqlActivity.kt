package com.tudorEnterprises.dndapp.dataStorage

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.tudorEnterprises.dndapp.dataStorage.databases.CharacterDatabase
import com.tudorEnterprises.dndapp.dataStorage.tables.CharacterNameData
import com.tudorEnterprises.dndapp.objects.SecureStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CharacterSqlActivity(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        CharacterDatabase::class.java,
        "character_database"
    )
        .fallbackToDestructiveMigration(true)
        .build()

    private val loggedInUser = SecureStorage.getUserId(context)

    suspend fun insertCharacterData(characterName: String, syncCharacterId: Int, updateTime: Long) {
        withContext(Dispatchers.IO) {
            try {
                db.characterDao.insertCharacter(
                    CharacterNameData(
                        characterName = characterName,
                        syncCharacterId = syncCharacterId,
                        userId = loggedInUser.toInt(),
                        updateTime = updateTime
                    )
                )
                Log.d(this::class.java.simpleName, "character saved successfully with id $syncCharacterId")
            } catch (ex: Exception) {
                Log.d(this::class.java.simpleName, "character save failed with exception: $ex")
            }
        }
    }

    fun getAllCharacters() : Flow<List<CharacterNameData>> {
        return db.characterDao.getAllCharacters(loggedInUser)
    }
}