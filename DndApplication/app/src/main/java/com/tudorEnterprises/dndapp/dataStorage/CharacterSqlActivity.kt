package com.tudorEnterprises.dndapp.dataStorage

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.tudorEnterprises.dndapp.dataModels.responses.Player
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
    fun getCharacterByIdFlow(characterId: Int) : Flow<CharacterNameData> {
        return db.characterDao.getCharacterByIdFlow(characterId)
    }
    fun getCharacterCampaign(characterId: Int) : Flow<String> {
        return db.characterDao.getCharacterCampaign(characterId)
    }
    suspend fun deleteCharacterById(id: Int) {
        db.characterDao.deleteCharacterById(id)
    }
    suspend fun getCharacterById(characterId: Int) : CharacterNameData? {
        return db.characterDao.getCharacterById(characterId)
    }
    suspend fun upsertCharacter(characterData: Player) {
        db.characterDao.upsertCharacter(CharacterNameData(characterName = characterData.name, syncCharacterId = characterData.id, userId = loggedInUser.toInt(), updateTime = characterData.updateTime, campaignId = characterData.campaignId))
    }
}