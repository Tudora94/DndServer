package com.tudorEnterprises.dndapp.dataStorage.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tudorEnterprises.dndapp.dataStorage.tables.CharacterNameData
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterNameData)

    @Query("SELECT * FROM CharacterNameData WHERE userId = :userId")
    fun getAllCharacters(userId: String) : Flow<List<CharacterNameData>>

    @Query("DELETE FROM CharacterNameData WHERE sync_Character_Id = :characterId")
    suspend fun deleteCharacterById(characterId: Int)
}