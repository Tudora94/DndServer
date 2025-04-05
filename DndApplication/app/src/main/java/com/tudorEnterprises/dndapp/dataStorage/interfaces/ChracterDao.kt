package com.tudorEnterprises.dndapp.dataStorage.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.tudorEnterprises.dndapp.dataStorage.tables.CharacterNameData
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterNameData)

    @Query("SELECT * FROM CharacterNameData WHERE userId = :userId")
    fun getAllCharacters(userId: String) : Flow<List<CharacterNameData>>

    @Query("SELECT * FROM CharacterNameData WHERE sync_Character_Id = :characterId")
    fun  getCharacterByIdFlow(characterId: Int) : Flow<CharacterNameData>

    @Query("SELECT campaign_name FROM CharacterNameData WHERE sync_Character_Id = :characterId")
    fun getCharacterCampaign(characterId: Int) : Flow<String>

    @Query("DELETE FROM CharacterNameData WHERE sync_Character_Id = :characterId")
    suspend fun deleteCharacterById(characterId: Int)

    @Query("SELECT * FROM CharacterNameData WHERE sync_Character_Id = :characterId")
    suspend fun  getCharacterById(characterId: Int) : CharacterNameData?

    @Upsert
    suspend fun upsertCharacter(character: CharacterNameData)
}